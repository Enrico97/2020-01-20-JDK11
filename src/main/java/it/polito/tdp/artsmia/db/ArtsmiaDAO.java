package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artists;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {
	
	Map<Integer, Artists> artisti = new HashMap<>();

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
		
	public List<String> ruoli() {
			
			String sql = "SELECT distinct role FROM authorship";
			List<String> result = new ArrayList<>();
			Connection conn = DBConnect.getConnection();
	
			try {
				PreparedStatement st = conn.prepareStatement(sql);
				ResultSet res = st.executeQuery();
				while (res.next()) {
	
					result.add(res.getString("role"));
				}
				conn.close();
				return result;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
	
	public List<Artists> vertici(String ruolo) {
		
		String sql = "SELECT * FROM artists WHERE artist_id in (SELECT distinct artist_id FROM authorship WHERE role=?)";
		List<Artists> vertici = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, ruolo);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Artists a = new Artists(res.getInt("artist_id"), res.getString("name"));
				vertici.add(a);
				artisti.put(res.getInt("artist_id"), a);
			}
			conn.close();
			return vertici;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenze> archi(String ruolo) {
			this.vertici(ruolo);
			String sql = "SELECT a1.artist_id as a1, a2.artist_id as a2, Count(distinct e1.exhibition_id) as peso FROM artists as a1, artists as a2, authorship as au1, authorship as au2, exhibition_objects as e1, exhibition_objects as e2 WHERE au1.artist_id=a1.artist_id and au2.artist_id=a2.artist_id and au1.role=? and au2.role=? and a1.artist_id>a2.artist_id and au1.object_id=e1.object_id and au2.object_id=e2.object_id and e1.exhibition_id=e2.exhibition_id group by a1.artist_id, a2.artist_id";
			List<Adiacenze> archi = new ArrayList<>();
			Connection conn = DBConnect.getConnection();
	
			try {
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, ruolo);
				st.setString(2, ruolo);
				ResultSet res = st.executeQuery();
				while (res.next()) {
					if (artisti.containsKey(res.getInt("a1")) && artisti.containsKey(res.getInt("a2")))
						archi.add(new Adiacenze(artisti.get(res.getInt("a1")), artisti.get(res.getInt("a2")), res.getInt("peso")));
					
				}
				conn.close();
				Collections.sort(archi);
				return archi;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
}
