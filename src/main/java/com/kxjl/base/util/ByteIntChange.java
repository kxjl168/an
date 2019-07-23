package com.kxjl.base.util;


public class ByteIntChange {
	public static Integer ByteFourToInt(byte[] m_src)
	{
		
		Integer dst = 0;
		for (int i = 0; i < 4; ++i) {  
			dst <<= 8;  
			dst |= (m_src[i] & 0xff);  
	    } 
		return dst;
	}
	
	public static Long ByteFourToLong(byte[] m_src)
	{
		Long dst = 0L;
		for (int i = 0; i < 4; ++i) {  
			dst <<= 8;  
			dst |= (m_src[i] & 0xff);  
	    }
		return dst;
		
	}
	
	public static double ByteToDouble(byte[] m_src) {
		long m_l = 0L;
		for (int i = 0; i < 8; ++i) {  
			m_l <<= 8;  
			m_l |= (m_src[7-i] & 0xff);  
		}
		return Double.longBitsToDouble(m_l);
	}
	
	public static int ByteTwoToInt(byte m_src1,byte m_src2)
	{
		int dst = m_src2 & 0xff;
		dst = dst + ((m_src1 << 8)&0xff00);
		return dst;
	}
	
	public static byte[] IntToByteTwo(Integer src)
	{
		byte[] m_dgt = new byte[2];
		m_dgt[1] = (byte) (src&0xff);
		m_dgt[0] = (byte) ((0xff00 & src) >> 8);
		return m_dgt;
	}
	
	public static byte[] IntToByteFour(int hexCrc32value) {
		byte[] result = new byte[4];
		result[0] = (byte)((hexCrc32value >> 24) & 0xFF);//
		result[1] = (byte)((hexCrc32value >> 16) & 0xFF);//
		result[2] = (byte) ((hexCrc32value >> 8) & 0xFF); //
		result[3] = (byte) (hexCrc32value & 0xFF);//
		return result;
	}
	
	public static byte[] BCDToByte(byte[] m_src){
		byte[] m_dest = new byte[m_src.length];
		for(int i=0;i<m_src.length;i++){
			int m_c1 = (m_src[i]&0xF0)>>4;
			int m_c2 = (m_src[i]&0x0F)<<4;
			m_dest[i] = (byte)( (m_c1+m_c2)&0xFF);
		}
		return m_dest;
	}
	
	public static byte[] ByteToBCD(byte[] m_src){
		byte[] m_dest = new byte[m_src.length];
		for(int i=0;i<m_src.length;i++){
			int m_c1 = (m_src[i]&0xF0)>>4;
			int m_c2 = (m_src[i]&0x0F)<<4;
			m_dest[i] = (byte)( (m_c1+m_c2)&0xFF);
		}
		return m_dest;
	}
	
	public static void printHexString(byte[] b) {
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			System.out.print(hex.toUpperCase());
		}
		System.out.println();

	}
	
	public static String ByteToHexString(byte[] src){
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<src.length;i++){
			String hex = Integer.toHexString(src[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			buffer.append(hex.toUpperCase());
			
		}
		return buffer.toString();
	}
}