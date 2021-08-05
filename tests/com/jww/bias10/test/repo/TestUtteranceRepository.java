package com.jww.bias10.test.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jww.bias10.model.Utterance;
import com.jww.bias10.repo.UtteranceRepository;


public class TestUtteranceRepository extends BaseRepositoryTest
{
	private Integer interviewId;
	private Integer speakerTurnId;
	private String speakerRole = "TST";
	private UtteranceRepository repo;
	
	@Before
	public void setUp() throws ClassNotFoundException, SQLException
	{
		PreparedStatement s = con.prepareStatement("DELETE FROM interview");
		s.executeUpdate();
		s = con.prepareStatement("DELETE FROM speaker_turn");
		s.executeUpdate();
		s = con.prepareStatement("DELETE FROM speaker_role");
		s.executeUpdate();
		con.commit();
		
		PreparedStatement ps;
		ResultSet rs;
		this.repo = new UtteranceRepository(con);
		
		
		//Insert an interview for testing
		ps = con.prepareStatement("INSERT INTO interview (interview_name) VALUES (?)", 
				Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, "TEST_INTERVIEW");
		ps.executeUpdate();
		
		rs = ps.getGeneratedKeys();
		rs.next();
		this.interviewId = rs.getInt(1);
		
		//Insert a SpeakerRole for testing
		ps = con.prepareStatement("INSERT INTO speaker_role (speaker_role, speaker_description) VALUES (?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, this.speakerRole);
		ps.setString(2, "TEST ROLE");
		ps.executeUpdate();
		
		//Insert SpeakerTurn for testing
		ps = con.prepareStatement("INSERT INTO speaker_turn (interview_id, st_line, speaker_role, st_text) " + 
		"VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, this.interviewId);
		ps.setInt(2, 1);
		ps.setString(3, this.speakerRole);
		ps.setString(4, "THIS IS SOME TEST TEXT");
		
		ps.executeUpdate();
		rs = ps.getGeneratedKeys();
		rs.next();
		this.speakerTurnId = rs.getInt(1);
		con.commit();
		
		
	}
	
	@After
	public void tearDown() throws SQLException
	{
		
		PreparedStatement s = con.prepareStatement("DELETE FROM interview");
		s.executeUpdate();
		s = con.prepareStatement("DELETE FROM speaker_turn");
		s.executeUpdate();
		s = con.prepareStatement("DELETE FROM speaker_role");
		s.executeUpdate();
		con.commit();
	}
	
	@Test
	public void testGet() throws SQLException
	{
		PreparedStatement ps;
		ResultSet r;
		
		//Test that retrieval of utterance by ID works
		Integer utt_id;
		Utterance u = new Utterance();
		Utterance u2;
		
		u.setSpeakerTurnId(this.speakerTurnId);
		u.setUttEnum(1);
		u.setStartTime(0d);
		u.setEndTime(3.291);
		u.setStartIndex(0);
		u.setEndIndex(14);
		
		u.setId(repo.insert(u));
		u2 = repo.get(u.getId());
		
		assertEquals(u.getId(), u2.getId());
		con.rollback();
		
		//Test that retrieval of all utterances works
		assertEquals(0, repo.getAll().size());
		
		Utterance u3 = new Utterance();
		u3.setSpeakerTurnId(this.speakerTurnId);
		u3.setUttEnum(1);
		u3.setStartTime(0d);
		u3.setEndTime(3.291);
		u3.setStartIndex(0);
		u3.setEndIndex(14);
		
		Utterance u4 = new Utterance();
		u4.setSpeakerTurnId(this.speakerTurnId);
		u4.setUttEnum(2);
		u4.setStartTime(3.291d);
		u4.setEndTime(4.444d);
		u4.setStartIndex(15);
		u4.setEndIndex(21);
		
		assertDoesNotThrow(() -> u3.setId(repo.insert(u3)));
		assertDoesNotThrow(() ->u4.setId(repo.insert(u4)));
		
		assertDoesNotThrow(() -> assertEquals(2, repo.getAll().size()));
		
		//Test that retrieval by interview works
		assertDoesNotThrow(() -> assertEquals(2, repo.getByInterview(this.interviewId).size()));
		con.rollback();
		assertDoesNotThrow(() -> assertEquals(0, repo.getByInterview(this.interviewId).size()));
	}
	
	@Test
	public void testInsert() throws SQLException
	{
		//Test that insertion of valid entity works
		Utterance u = new Utterance();
		u.setSpeakerTurnId(this.speakerTurnId);
		u.setUttEnum(1);
		u.setStartTime(0d);
		u.setEndTime(0.5d);
		u.setStartIndex(0);
		u.setEndIndex(15);
		assertDoesNotThrow(() -> u.setId(repo.insert(u)));
		
		//Test that insertion of entity with duplicate values does not work
		Utterance u2 = new Utterance();
		u2.setSpeakerTurnId(this.speakerTurnId); // should trigger check violation
		u2.setUttEnum(1); //should trigger check violation
		u2.setStartTime(4d);
		u2.setEndTime(5.5d);
		u2.setStartIndex(16);
		u2.setEndIndex(22);
		assertThrows(SQLIntegrityConstraintViolationException.class, () -> u2.setId(repo.insert(u2)));
		con.rollback();
		
		
		//Test that insertion of entity with null FK reference fails
		Utterance u3 = new Utterance();
		u3.setUttEnum(1); //should trigger check violation
		u3.setStartTime(4d);
		u3.setEndTime(5.5d);
		u3.setStartIndex(16);
		u3.setEndIndex(22);
		assertThrows(SQLIntegrityConstraintViolationException.class, () -> u3.setId(repo.insert(u3)));
		con.rollback();
		
		//Test that insertion of entity with null utt_enum fails
		Utterance u4 = new Utterance();
		u4.setSpeakerTurnId(this.speakerTurnId);
		u4.setStartTime(4d);
		u4.setEndTime(5.5d);
		u4.setStartIndex(16);
		u4.setEndIndex(22);
		assertThrows(SQLIntegrityConstraintViolationException.class, () -> u3.setId(repo.insert(u3)));
		con.rollback();
	}
	
	@Test
	public void testDelete() throws SQLException
	{
		//Test that deletion of utterance with valid PK succeeds
		Utterance u = new Utterance();
		u.setSpeakerTurnId(this.speakerTurnId);
		u.setUttEnum(1);
		u.setStartTime(0d);
		u.setEndTime(0.5d);
		u.setStartIndex(0);
		u.setEndIndex(15);
		assertDoesNotThrow(() -> u.setId(repo.insert(u)));
		assertDoesNotThrow(()-> assertEquals(repo.delete(u).intValue(), 1));
		con.rollback();
		
		//Test that deletion of utterance with invalid pk does nothing
		Utterance u2 = new Utterance();
		u2.setSpeakerTurnId(this.speakerTurnId);
		u2.setUttEnum(1);
		u2.setStartTime(0d);
		u2.setEndTime(0.5d);
		u2.setStartIndex(0);
		u2.setEndIndex(15);
		assertDoesNotThrow(() -> u.setId(repo.insert(u)));
		
		u.setId(0);
		assertDoesNotThrow(()-> assertEquals(repo.delete(u).intValue(), 0));
		con.rollback();
		
		
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		//Test that update to invalid speaker_turn PK fails
		Utterance u = new Utterance();
		u.setSpeakerTurnId(this.speakerTurnId);
		u.setUttEnum(1);
		u.setStartTime(0d);
		u.setEndTime(0.5d);
		u.setStartIndex(0);
		u.setEndIndex(15);
		assertDoesNotThrow(() -> u.setId(repo.insert(u)));
		
		u.setSpeakerTurnId(0);
		assertThrows(SQLIntegrityConstraintViolationException.class, () -> repo.update(u));
		con.rollback();
		
		//Test that update to null speaker_turn PK fails
		Utterance u2 = new Utterance();
		u2.setSpeakerTurnId(this.speakerTurnId);
		u2.setUttEnum(1);
		u2.setStartTime(0d);
		u2.setEndTime(0.5d);
		u2.setStartIndex(0);
		u2.setEndIndex(15);
		assertDoesNotThrow(() -> u2.setId(repo.insert(u2)));
		
		u2.setSpeakerTurnId(null);
		assertThrows(SQLIntegrityConstraintViolationException.class, () -> repo.update(u2));
		con.rollback();
		
		//Test that update to null utt_enum fails
		Utterance u3 = new Utterance();
		u3.setSpeakerTurnId(this.speakerTurnId);
		u3.setUttEnum(1);
		u3.setStartTime(0d);
		u3.setEndTime(0.5d);
		u3.setStartIndex(0);
		u3.setEndIndex(15);
		assertDoesNotThrow(() -> u3.setId(repo.insert(u3)));
		
		u3.setUttEnum(null);
		assertThrows(SQLIntegrityConstraintViolationException.class, () -> repo.update(u3));
		con.rollback();
		
	}
	
	
	
}
