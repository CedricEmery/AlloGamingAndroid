package fr.epsi.emery_legoff_itier.allogaming;

import java.util.ArrayList;
import java.util.List;

public class Game {

	protected int m_iGameId;
	protected String m_sGameName;
	protected String m_sGamePlatform;
	protected String m_sGameOverview;
	protected List<String> m_sGameGenreList;
	protected String m_sGamePlayerNb;
	protected String m_sGamePublisher;
	protected String m_sGameDeveloper;
	protected String m_sGameReleaseDate;
	protected String m_sGameRating;
	protected List<Image> m_GameImageList;
	
	
	
	/**
	 * @return the iGameId
	 */
	public int getGameId() {
		return m_iGameId;
	}
	/**
	 * @param iGameId the iGameId to set
	 */
	public void setGameId(int iGameId) {
		this.m_iGameId = iGameId;
	}
	/**
	 * @return the sGameName
	 */
	public String getGameName() {
		return m_sGameName;
	}
	/**
	 * @param sGameName the sGameName to set
	 */
	public void setGameName(String sGameName) {
		this.m_sGameName = sGameName;
	}
	/**
	 * @return the sGamePlatform
	 */
	public String getGamePlatform() {
		return m_sGamePlatform;
	}
	/**
	 * @param sGamePlatform the sGamePlatform to set
	 */
	public void setGamePlatform(String sGamePlatform) {
		this.m_sGamePlatform = sGamePlatform;
	}
	/**
	 * @return the sGameOverview
	 */
	public String getGameOverview() {
		return m_sGameOverview;
	}
	/**
	 * @param sGameOverview the sGameOverview to set
	 */
	public void setGameOverview(String sGameOverview) {
		this.m_sGameOverview = sGameOverview;
	}
	/**
	 * @return the sGameGenreList
	 */
	public List<String> getGameGenreList() {
		return m_sGameGenreList;
	}
	/**
	 * @param sGameGenreList the sGameGenreList to set
	 */
	public void setGameGenreList(List<String> sGameGenreList) {
		this.m_sGameGenreList = sGameGenreList;
	}
	/**
	 * @return the sGamePlayerNb
	 */
	public String getGamePlayerNb() {
		return m_sGamePlayerNb;
	}
	/**
	 * @param sGamePlayerNb the sGamePlayerNb to set
	 */
	public void setGamePlayerNb(String sGamePlayerNb) {
		this.m_sGamePlayerNb = sGamePlayerNb;
	}
	/**
	 * @return the sGamePublisher
	 */
	public String getGamePublisher() {
		return m_sGamePublisher;
	}
	/**
	 * @param sGamePublisher the sGamePublisher to set
	 */
	public void setGamePublisher(String sGamePublisher) {
		this.m_sGamePublisher = sGamePublisher;
	}
	/**
	 * @return the sGameDeveloper
	 */
	public String getGameDeveloper() {
		return m_sGameDeveloper;
	}
	/**
	 * @param sGameDeveloper the sGameDeveloper to set
	 */
	public void setGameDeveloper(String sGameDeveloper) {
		this.m_sGameDeveloper = sGameDeveloper;
	}
	/**
	 * @return the sGameReleaseDate
	 */
	public String getGameReleaseDate() {
		return m_sGameReleaseDate;
	}
	/**
	 * @param sGameReleaseDate the sGameReleaseDate to set
	 */
	public void setGameReleaseDate(String sGameReleaseDate) {
		this.m_sGameReleaseDate = sGameReleaseDate;
	}
	/**
	 * @return the sGameRating
	 */
	public String getGameRating() {
		return m_sGameRating;
	}
	/**
	 * @param sGameRating the sGameRating to set
	 */
	public void setGameRating(String sGameRating) {
		this.m_sGameRating = sGameRating;
	}
	/**
	 * @return the gameImageList
	 */
	public List<Image> getGameImageList() {
		return m_GameImageList;
	}
	/**
	 * @param gameImageList the gameImageList to set
	 */
	public void setGameImageList(List<Image> gameImageList) {
		m_GameImageList = gameImageList;
	}
}