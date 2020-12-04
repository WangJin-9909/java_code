package cn.com.icbc.ms.token.utils;

import java.io.IOException;
import java.io.OutputStream;

public class Base64Encoder {
	protected final byte[] encodingTable = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".getBytes();

	  protected byte padding = 61;

	  protected final byte[] decodingTable = new byte['聙'];

	  protected void initialiseDecodingTable()
	  {
	    for (int i = 0; i < this.decodingTable.length; i++)
	    {
	      this.decodingTable[i] = -1;
	    }

	    for (int i = 0; i < this.encodingTable.length; i++)
	    {
	      this.decodingTable[this.encodingTable[i]] = (byte)i;
	    }
	  }

	  public Base64Encoder()
	  {
	    initialiseDecodingTable();
	  }

	  public int encode(byte[] data, int off, int length, OutputStream out)
	    throws IOException
	  {
		  if(null == data || null == out) {
			  throw new  IllegalArgumentException("param is null");
		  }
	    int modulus = length % 3;
	    int dataLength = length - modulus;

	    for (int i = off; i < off + dataLength; i += 3)
	    {
	      int a1 = data[i] & 0xFF;
	      int a2 = data[(i + 1)] & 0xFF;
	      int a3 = data[(i + 2)] & 0xFF;

	      out.write(this.encodingTable[(a1 >>> 2 & 0x3F)]);
	      out.write(this.encodingTable[((a1 << 4 | a2 >>> 4) & 0x3F)]);
	      out.write(this.encodingTable[((a2 << 2 | a3 >>> 6) & 0x3F)]);
	      out.write(this.encodingTable[(a3 & 0x3F)]);
	    }
	    int d1;
	    int b1;
	    int b2;
	    switch (modulus)
	    {
	    case 0:
	      break;
	    case 1:
	      d1 = data[(off + dataLength)] & 0xFF;
	      b1 = d1 >>> 2 & 0x3F;
	      b2 = d1 << 4 & 0x3F;

	      out.write(this.encodingTable[b1]);
	      out.write(this.encodingTable[b2]);
	      out.write(this.padding);
	      out.write(this.padding);
	      break;
	    case 2:
	      d1 = data[(off + dataLength)] & 0xFF;
	      int d2 = data[(off + dataLength + 1)] & 0xFF;

	      b1 = d1 >>> 2 & 0x3F;
	      b2 = (d1 << 4 | d2 >>> 4) & 0x3F;
	      int b3 = d2 << 2 & 0x3F;

	      out.write(this.encodingTable[b1]);
	      out.write(this.encodingTable[b2]);
	      out.write(this.encodingTable[b3]);
	      out.write(this.padding);
	    }

	    return dataLength / 3 * 4 + (modulus == 0 ? 0 : 4);
	  }

	  private boolean ignore(char c)
	  {
	    return (c == '\n') || (c == '\r') || (c == '\t') || (c == ' ');
	  }

	  public int decode(byte[] data, int off, int length, OutputStream out)
	    throws IOException
	  {
		  if(null == data || null == out) {
			  throw new IllegalArgumentException("param is null");
		  }
	    int outLen = 0;

	    int end = off + length;

	    while (end > off)
	    {
	      if (!ignore((char)data[(end - 1)]))
	      {
	        break;
	      }

	      end--;
	    }

	    int i = off;
	    int finish = end - 4;

	    i = nextI(data, i, finish);

	    while (i < finish)
	    {
	      byte b1 = this.decodingTable[data[(i++)]];

	      i = nextI(data, i, finish);

	      byte b2 = this.decodingTable[data[(i++)]];

	      i = nextI(data, i, finish);

	      byte b3 = this.decodingTable[data[(i++)]];

	      i = nextI(data, i, finish);

	      byte b4 = this.decodingTable[data[(i++)]];

	      if ((b1 | b2 | b3 | b4) < 0)
	      {
	        throw new IOException("invalid characters encountered in base64 data");
	      }

	      out.write(b1 << 2 | b2 >> 4);
	      out.write(b2 << 4 | b3 >> 2);
	      out.write(b3 << 6 | b4);

	      outLen += 3;

	      i = nextI(data, i, finish);
	    }

	    outLen += decodeLastBlock(out, (char)data[(end - 4)], (char)data[(end - 3)], (char)data[(end - 2)], (char)data[(end - 1)]);

	    return outLen;
	  }

	  private int nextI(byte[] data, int i, int finish)
	  {
		  if(null == data ) {
			  throw new IllegalArgumentException("param is null");
		  }
	    while ((i < finish) && (ignore((char)data[i])))
	    {
	      i++;
	    }
	    return i;
	  }

	  public int decode(String data, OutputStream out)
	    throws IOException
	  {
		  if(null == data || null == out) {
			  throw new IllegalArgumentException("param is null");
		  }
	    int length = 0;

	    int end = data.length();

	    while (end > 0)
	    {
	      if (!ignore(data.charAt(end - 1)))
	      {
	        break;
	      }

	      end--;
	    }

	    int i = 0;
	    int finish = end - 4;

	    i = nextI(data, i, finish);

	    while (i < finish)
	    {
	      byte b1 = this.decodingTable[data.charAt(i++)];

	      i = nextI(data, i, finish);

	      byte b2 = this.decodingTable[data.charAt(i++)];

	      i = nextI(data, i, finish);

	      byte b3 = this.decodingTable[data.charAt(i++)];

	      i = nextI(data, i, finish);

	      byte b4 = this.decodingTable[data.charAt(i++)];

	      if ((b1 | b2 | b3 | b4) < 0)
	      {
	        throw new IOException("invalid characters encountered in base64 data");
	      }

	      out.write(b1 << 2 | b2 >> 4);
	      out.write(b2 << 4 | b3 >> 2);
	      out.write(b3 << 6 | b4);

	      length += 3;

	      i = nextI(data, i, finish);
	    }

	    length += decodeLastBlock(out, data.charAt(end - 4), data.charAt(end - 3), data.charAt(end - 2), data.charAt(end - 1));

	    return length;
	  }

	  private int decodeLastBlock(OutputStream out, char c1, char c2, char c3, char c4)
			    throws IOException
			  {
				  if(null == out){
					  return 0;//确认下返回0 行嘛？
				  }
			    if (c3 == this.padding)
			    {
			      byte b1 = this.decodingTable[c1];
			      byte b2 = this.decodingTable[c2];

			      if ((b1 | b2) < 0)
			      {
			        throw new IOException("invalid characters encountered at end of base64 data");
			      }

			      out.write(b1 << 2 | b2 >> 4);

			      return 1;
			    }
			    if (c4 == this.padding)
			    {
			      byte b1 = this.decodingTable[c1];
			      byte b2 = this.decodingTable[c2];
			      byte b3 = this.decodingTable[c3];

			      if ((b1 | b2 | b3) < 0)
			      {
			        throw new IOException("invalid characters encountered at end of base64 data");
			      }

			      out.write(b1 << 2 | b2 >> 4);
			      out.write(b2 << 4 | b3 >> 2);

			      return 2;
			    }

			    byte b1 = this.decodingTable[c1];
			    byte b2 = this.decodingTable[c2];
			    byte b3 = this.decodingTable[c3];
			    //byte b4 = this.decodingTable[c4];
			    //sonar 
			   // int b1 = (int)this.decodingTable[c1];
			   // int b2 = (int)this.decodingTable[c2];
			   // int b3 = (int)this.decodingTable[c3];
			    int b4 = (int)this.decodingTable[c4];

			    if ((b1 | b2 | b3 | b4) < 0)
			    {
			      throw new IOException("invalid characters encountered at end of base64 data");
			    }

			  //  if(out != null){
			    out.write(b1 << 2 | b2 >> 4);
			    out.write(b2 << 4 | b3 >> 2);
			    out.write(b3 << 6 | b4);
			    //}
			    return 3;
			  }

			  private int nextI(String data, int i, int finish)
			  {
				  if(null == data){
					  return 0;
				  }
			    while ((i < finish) && (ignore(data.charAt(i))))
			    {
			      i++;
			    }
			    return i;
			  }
}
