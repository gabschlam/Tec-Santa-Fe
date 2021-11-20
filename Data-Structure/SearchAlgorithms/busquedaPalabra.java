/*
    Algoritmos de Búsqueda: Búsqueda de Palabras
    Estructura de Datos
 
    Gabriel Schlam
*/

import java.util.Arrays;
import java.util.Scanner;

public class busquedaPalabra
{
	public static void main(String[] args)
	{
		String[] arreglo = {"ABA", "ABC", "ACA", "ACHI", "ACHU", "AES", "AGA", "AHE", "AHI", "AHO", "AJA", "AJE", "AJI", "AJO", "ALA", "ALE", "ALI", "ALLA", "ALLI", "AMA", "AME", "AMI", "AMO", "ANA", "ANO", "AÑA", "AÑO", "APA", "API", "ARA", "ARE", "ARO", "ARRA", "ARRE", "ASA", "ASE", "ASI", "ASO", "ATA", "ATE", "ATO", "AUN", "AVE", "AVO", "AYA", "AYO", "AZO", "BAH", "BAO", "BAR", "BEL", "BEN", "BES", "BEY", "BIO", "BIS", "BIT", "BLA", "BLE", "BOA", "BOJ", "BOL", "BON", "BOU", "BOX", "BOY", "BUA", "BUE", "BUM", "BUS", "BUZ", "CAE", "CAI", "CAL", "CAN", "CAO", "CAP", "CAR", "CAS", "CAY", "CAZ", "CEA", "CEO", "CES", "CIA", "CID", "CIE", "CIO", "CLO", "COA", "COL", "CON", "COR", "COY", "COZ", "CUI", "CUS", "CUY", "CUZ", "CHAI", "CHAL", "CHAN", "CHAO", "CHAS", "CHAT", "CHAU", "CHEF", "CHES", "CHIA", "CHIC", "CHIE", "CHII", "CHIN", "CHIO", "CHIP", "CHIS", "CHOP", "CHOZ", "CHUA", "CHUJ", "CHUS", "CHUT", "CHUZ", "DAD", "DAN", "DAR", "DAS", "DEA", "DEL", "DEN", "DES", "DEY", "DIA", "DIJ", "DIN", "DIO", "DIU", "DIX", "DOM", "DON", "DOS", "DOY", "DUA", "DUO", "DUX", "DUZ", "ECO", "ECU", "ECHA", "ECHE", "ECHO", "EES", "EFE", "EGO", "EJE", "ELE", "ELLA", "ELLE", "ELLO", "EME", "EMU", "ENE", "EÑE", "EON", "EPA", "EPO", "ERA", "ERE", "ERG", "ERO", "ERRA", "ERRE", "ERRO", "ESA", "ESE", "ESO", "ETA", "EVO", "FAI", "FAN", "FAR", "FAS", "FAX", "FAZ", "FEA", "FEO", "FER", "FES", "FEZ", "FIA", "FIE", "FIL", "FIN", "FIO", "FIS", "FON", "FUA", "FUE", "FUI", "FUL", "FULL", "GAG", "GAL", "GAP", "GAS", "GAY", "GEA", "GEL", "GEN", "GEO", "GES", "GIL", "GIN", "GIS", "GOL", "GRO", "GUA", "HALL", "HAN", "HAO", "HAS", "HAY", "HAZ", "HER", "HEZ", "HIN", "HOY", "HOZ", "HUI", "HUM", "HUY", "IBA", "ICHO", "ICHU", "ICE", "IDA", "IDO", "IES", "ION", "IRA", "IRE", "ISA", "IZA", "IZO", "JAI", "JAN", "JAU", "JEA", "JET", "JIS", "JUR", "LAR", "LAS", "LAY", "LEA", "LED", "LEE", "LEI", "LEN", "LEO", "LES", "LEV", "LEY", "LIA", "LID", "LIE", "LIO", "LIS", "LOA", "LOE", "LOO", "LOS", "LUA", "LUE", "LUI", "LUX", "LUZ", "LLAR", "LLES", "MACH", "MAL", "MAM", "MAN", "MAR", "MAS", "MEA", "MEE", "MEO", "MES", "MIA", "MIE", "MIL", "MIO", "MIR", "MIS", "MIZ", "MOA", "MOD", "MOL", "MOR", "MUA", "MUE", "MUI", "MUR", "MUS", "MUY", "NAO", "NAS", "NEA", "NEN", "NEO", "NIA", "NIN", "NIS", "NON", "NOS", "ÑAS", "ÑOR", "ÑOS", "ÑUS", "OAS", "OBO", "OCA", "OCHO", "ODA", "OES", "OFF", "OHM", "OIA", "OID", "OIL", "OIR", "OIS", "OJO", "OLA", "OLE", "OLI", "OLLA", "ONA", "OPA", "OPE", "OPO", "ORA", "ORE", "ORI", "ORO", "OSA", "OSE", "OSO", "OTO", "OVA", "OVE", "OVO", "OXE", "OYE", "OYO", "PAF", "PAL", "PAN", "PAO", "PAR", "PAZ", "PCHE", "PCHS", "PEA", "PECH", "PEE", "PEI", "PEO", "PES", "PEZ", "PIA", "PIE", "PIN", "PIO", "PIS", "PLE", "POA", "POCH", "POM", "PON", "POP", "POR", "POS", "PRE", "PRO", "PSI", "PUA", "PUB", "PUE", "PUF", "PUM", "PUN", "PUO", "PUS", "PUT", "QUE", "QUI", "RAD", "RAE", "RAI", "RAP", "RAS", "REA", "RED", "REI", "REO", "RES", "REY", "RIA", "RIE", "RIN", "RIO", "ROA", "ROB", "ROE", "ROI", "ROL", "RON", "ROO", "ROS", "RUA", "RUC", "RUE", "RUN", "RUO", "RUS", "SAH", "SAL", "SAN", "SAO", "SAZ", "SEA", "SED", "SEL", "SEN", "SEO", "SER", "SES", "SET", "SIC", "SIJ", "SIL", "SIN", "SIS", "SIU", "SOL", "SON", "SOR", "SOS", "SOY", "SPA", "SUD", "SUR", "SUS", "TAC", "TAL", "TAN", "TAO", "TAS", "TAU", "TAZ", "TEA", "TELL", "TEN", "TER", "TES", "TEX", "TEZ", "TIA", "TIC", "TIO", "TOA", "TOE", "TOL", "TON", "TOO", "TOP", "TOS", "TUL", "TUN", "TUP", "TUS", "UBE", "UBI", "UCE", "UCHU", "UCI", "UES", "UFA", "UFO", "UJU", "UNA", "UNE", "UNI", "UNO", "UÑA", "UÑE", "UÑI", "UÑO", "UPA", "UPE", "UPO", "URA", "URO", "USA", "USE", "USO", "UTA", "UVA", "UVE", "UVI", "UZO", "VAL", "VAN", "VAS", "VEA", "VED", "VEN", "VEO", "VER", "VES", "VEZ", "VIA", "VID", "VIL", "VIO", "VIP", "VIS", "VOS", "VOY", "VOZ", "XIS", "YAL", "YAZ", "YEN", "YES", "YIN", "YIP", "YOD", "YOS", "ZAR", "ZAS", "ZEN", "ZIS", "ZOO", "ZUA", "ZUM"};

		String palabra;

		int busqueda = 0;

		Scanner lector = new Scanner(System.in);
		
		System.out.println("Escribe la palabra");

		palabra = lector.nextLine();

		for (int i = 0; i< arreglo.length; i++) 
		{
			if (palabra.equals(arreglo[i])) 
			{
				busqueda = 1;
				System.out.println("Palabra encontrada en la posicion: " + i);
				break;
			}
		}

		if (busqueda != 1) 
		{
			System.out.println("La palabra no se encuentra en el arreglo");
		}

	}
}