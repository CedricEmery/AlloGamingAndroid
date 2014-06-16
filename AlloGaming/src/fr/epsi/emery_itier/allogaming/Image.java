package fr.epsi.emery_itier.allogaming;

public class Image {


	// http://thegamesdb.net/banners/ + FIN URL
	protected String sImageUrl;
	protected int iImageWidth;
	protected int iImageHeight;
	
	
	
	/**
	 * @return the sImageUrl
	 */
	public String getsImageUrl() {
		return sImageUrl;
	}
	/**
	 * @param sImageUrl the sImageUrl to set
	 */
	public void setsImageUrl(String sImageUrl) {
		this.sImageUrl = sImageUrl;
	}
	/**
	 * @return the iImageWidth
	 */
	public int getiImageWidth() {
		return iImageWidth;
	}
	/**
	 * @param iImageWidth the iImageWidth to set
	 */
	public void setiImageWidth(int iImageWidth) {
		this.iImageWidth = iImageWidth;
	}
	/**
	 * @return the iImageHeight
	 */
	public int getiImageHeight() {
		return iImageHeight;
	}
	/**
	 * @param iImageHeight the iImageHeight to set
	 */
	public void setiImageHeight(int iImageHeight) {
		this.iImageHeight = iImageHeight;
	}
}
