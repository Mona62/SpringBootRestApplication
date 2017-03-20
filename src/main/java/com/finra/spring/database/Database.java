/*
 * A static map to handle the metadata of the files uploaded.
 * 
 */


package com.finra.spring.database;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.finra.spring.model.*;

public class Database {

	private static  Map<Long,FileMetadata> filemetadata = new ConcurrentHashMap<>();

	public static  Map<Long,FileMetadata> getFilemetadata() {
		return filemetadata;
	}
	

}
