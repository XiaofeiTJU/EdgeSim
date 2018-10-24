package cn.edu.tju.simulation.cache;

import java.util.Iterator;
import java.util.LinkedList;

import cn.edu.tju.simulation.content.CachingSingleContent;
import cn.edu.tju.simulation.content.SingleContent;
import cn.edu.tju.simulation.content.SingleLocalHobby;
import cn.edu.tju.simulation.user.MobilityModel;
import cn.edu.tju.simulation.wirelessnetwork.WirelessNetwork;

/**
 * Cache abstract classes that contain all the properties and methods for
 * caching. When the base station inherits this class, we can configure the base
 * station cache.
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin
 *         University
 */
public abstract class Cache {
	/**
	 * Indicates whether there is a cache
	 */
	public Boolean hasCache = false;
	/**
	 * the size of the cache
	 */
	public long cacheSize;
	/**
	 * A list of contents in the cache, each cached content is an object that
	 * contains the following properties: content; Status (being downloaded or
	 * not downloaded); Number of remaining time slices.
	 **/
	public LinkedList<CachingSingleContent> cacheContent;
	/**
	 * The number of requests that the cache receives.
	 */
	public int cacheRequestAmount;
	/**
	 * The number of hit resources
	 */
	public int hitAmount;

	/**
	 * Processing the request in the cache, returns true if the cache contains
	 * the requested content, or false if not included. This method specifically
	 * refers to the user directly query the base station.
	 */
	public boolean dealQuery(Object object, SingleLocalHobby singleContent) {
		if (cacheContent != null) {
			if (object instanceof WirelessNetwork) {
				if (cacheContent.contains(singleContent)) {
					return true;
				} else {
					return false;
				}
			} else if (object instanceof MobilityModel) {
				if (cacheContent.contains(singleContent)) {
					return true;
				} else {
					return false;
				}
			}else{
				return false;
			}
		} else {
			return false;
		}
	}

	public Boolean removeCacheContent(SingleContent sc) {
		Iterator<CachingSingleContent> it = this.cacheContent.iterator();
		while (it.hasNext()) {
			CachingSingleContent csc = it.next();
			if (csc.getSingleContent().getName().equals(sc.getName())) {
				it.remove();
				return true;
			}
		}
		return false;
	}

	public Boolean removeCacheContent(int index) {
		this.cacheContent.remove(index);
		return true;
	}

	public Boolean addCacheContent(SingleLocalHobby sc) {
		this.cacheContent.add(new CachingSingleContent(sc.getSingleContent()));
		return true;
	}

	public long getRemainingCacheSize() {
		if (cacheContent.size() == 0) {
			return this.cacheSize;
		} else {
			long remainingCachesize = this.cacheSize;
			Iterator<CachingSingleContent> it = cacheContent.iterator();
			while (it.hasNext()) {
				CachingSingleContent cachingSingleContent = it.next();
				remainingCachesize -= cachingSingleContent.getSize();
			}
			return remainingCachesize;
		}
	}

	/**
	 * A new request arrives and the number of requests is incremented by 1.
	 */
	public void addRequestAmount() {
		cacheRequestAmount += 1;
	}

	/**
	 * Initialize the number of requests and hits.
	 */
	public void resetAmountOfRequestAndHits() {
		this.cacheRequestAmount = 0;
		this.hitAmount = 0;
	}

	/**
	 * The request is hit and the number of hits is incremented by 1.
	 */
	public void addHitAmount() {
		cacheRequestAmount += 1;
		hitAmount += 1;
	}

	/**
	 * See if this base station caontains a cache
	 * 
	 * @return True means there is a cache, or false means there is no cache.
	 */
	public Boolean getHasCache() {
		return hasCache;
	}

	public long getCacheSize() {
		return cacheSize;
	}

	public LinkedList<CachingSingleContent> getCacheContent() {
		return cacheContent;
	}

	public void setCacheContent(LinkedList<CachingSingleContent> cacheContent) {
		this.cacheContent = cacheContent;
	}

	public int getRequestAmount() {
		return cacheRequestAmount;
	}

	public int getHitAmount() {
		return hitAmount;
	}

	public void setCacheSize(long cacheSize) {
		this.cacheSize = cacheSize;
	}

}
