package example;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Base64;

//import com.icbc.ms.component.CommonDecryptUtils;

public class D {
	static RSAPrivateKey rpri;
	public static void main(String[] args){
		//CommonDecryptUtils utils = new CommonDecryptUtils();
		String strP = "I24lmrkRzbP2cS9oNTQBipXqwQV3QjqFSgKR3N4jHZAHTA65nCBBDyJ9EUTvbXxXXhvOWjHt4waaS3p2qbXTsZJC+ZEu1z4kKGeHpXz9CgcqnPbp2PKxisDkiZm3x9LrOSQE/NTVDJMa3T1T5M+sp0z7WlRu8QUQBcF3JrStvuU=";
		
		
		initKey();
		//String rsDt = utils.getArsDt(rsEt, "23154185067720174866394355456646326235039368390520308355251052486216470870486763846313612200704408711284115785006661152500159045854759820143048883429333458482858286851189326636190499186054248105020990317240780359558647027035008935550479612156606650808579387047032130501030002855661512577689663401138030549814133922946268432345786278398284585094287178208897946090624051454710621052173506157110125203327266767197401139892388551765248348892568755539668634253830038228257526591257721434166081334054854534435423045867734601109035358043118578225780829365467623795999142493277317848221295628901385809750227931617585622955729", "21642416785608167477388123145143058921007089565687978534659337064568847713265455844751503062251105006879203575963776933333151392815827350387153646049012145806840913315076304546740888340011155994588947081092142198541947290570373641972493096746714225784395930416819418805134441840977050177519773572591261049796228950424733568563904112563122103160367644491147950752897663328286082318887331570431691417110526425215642547601285061440504759028388001659186845828774439469835357323384448684325844866300792862072734420431893715204263662152350006986569936948986732726744199037266325279592137240385635641326914580922514007809257");
		
		String data = new String(Base64.getDecoder().decode(strP));
		String modulus = rpri.getModulus().toString(10);
		String exponent = new BigInteger(1, rpri.getEncoded()).toString(10);
		
		
		//String str = utils.getArsDt(data, modulus, exponent);
		//System.out.println("str = " + str);
	}

	public static String printHexString(byte[] b) {
		String str = "";
		for (int i = 0; i < b.length; i++) { 
		String hex = Integer.toHexString(b[i] & 0xFF); 
			if (hex.length() == 1) { 
				hex = '0' + hex; 
			} 
				//System.out.print(hex.toUpperCase());
				str+=hex.toUpperCase();
			} 
		
		return str;

		} 
	
	private static void initKey() {
		// TODO Auto-generated method stub
		PrivateKey privateKey = JKSUtil.getPrivateKey("D:\\rsa1024.jks", "yiersansi", "test1", "yiersansi");
		//PublicKey publicKey = JKSUtil.getPublicKey("D:\\rsa1024.jks", "yiersansi", "test1");
		rpri = (RSAPrivateKey) privateKey;
	}
}
