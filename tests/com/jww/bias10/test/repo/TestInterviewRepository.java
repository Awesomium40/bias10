package com.jww.bias10.test.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.junit.Before;
import org.junit.Test;

import com.jww.bias10.model.Interview;
import com.jww.bias10.repo.InterviewRepository;

public class TestInterviewRepository extends BaseRepositoryTest
{
	private InterviewRepository repo;
	
	@Before
	public void setUp() throws ClassNotFoundException, SQLException
	{
		this.repo = new InterviewRepository(con);
	}
	
	@Test
	public void testInsert() throws SQLException
	{	
		//Test that an entity with a null name can't be inserted
		assertThrows(SQLException.class, () -> repo.insert(new Interview()));
		con.rollback();
		
		//Test that interview whose name is empty
		Interview iv9 = new Interview();
		iv9.setName("");
		assertThrows(SQLException.class, () -> repo.insert(iv9));
		con.rollback();
				
		//Test that interview whose name is empty
		Interview iv = new Interview();
		iv.setName("");
		assertThrows(SQLException.class, () -> repo.insert(iv));
		con.rollback();
		
		//Test that a valid interview can be inserted
		Interview iv2 = new Interview();
		iv2.setName("TEST_INTERVIEW");
		assertDoesNotThrow(() -> repo.insert(iv2));
		
		//Test that interview with duplicate name can't be inserted
		assertThrows(SQLException.class, () -> repo.insert(iv2));
		con.rollback();
	}
	
	@Test 
	public void testUpdate() throws SQLException
	{
		//Test that an interview can't be updated to a null name
		Interview iv = new Interview();
		assertThrows(SQLIntegrityConstraintViolationException.class, () -> repo.insert(iv));
		
		iv.setName(null);
		assertThrows(SQLException.class, () -> repo.insert(iv));
		con.rollback();
		
		//Test that an interview can't be renamed to empty string
		Interview iv2 = new Interview();
		iv2.setName("TEST_INTERVIEW");
		assertDoesNotThrow(() -> repo.insert(iv2));
		
		iv2.setName("");
		assertThrows(SQLException.class, () -> repo.insert(iv2));
		con.rollback();
		
		
		//Test that interview can't be renamed into a duplicate
		Interview iv3 = new Interview();
		Interview iv4 = new Interview();
		
		iv3.setName("TEST_INTERVIEW");
		iv4.setName("TEST_INTERVIEW2");
		
		assertDoesNotThrow(() -> repo.insert(iv3));
		assertDoesNotThrow(() -> repo.insert(iv4));
		
		iv3.setName(iv4.getName());
		
		assertThrows(SQLException.class, () -> repo.insert(iv3));
		con.rollback();
		
	}
	
	@Test
	public void testDelete() throws SQLException
	{
		//Test that trying to delete non-existing interview does nothing
		Interview iv = new Interview();
		iv.setId(-99);
		
		assertEquals(0, repo.delete(iv).intValue());
		con.rollback();
		
		//Test that valid entity can be deleted
		Interview iv2 = new Interview();
		iv2.setName("TEST_INTERVIEW");
		assertDoesNotThrow(() -> iv2.setId(repo.insert(iv2)));
		
		assertDoesNotThrow(() -> repo.delete(iv2));
		con.rollback();
		
		//Test deleting interviews with null keys does nothing
		Interview iv3 = new Interview();
		assertDoesNotThrow(() -> repo.delete(iv3));
		con.rollback();
	}
	
	@Test
	public void testGet() throws SQLException
	{
		//Test can retrieve a valid entity
		Interview iv = new Interview();
		iv.setName("TEST_INTERVIEW");
		
		assertDoesNotThrow(() -> iv.setId(repo.insert(iv)));
		
		Interview iv2 = repo.get(iv.getId());
		
		assertEquals(iv, iv2);
		assertEquals(iv2.getName(), "TEST_INTERVIEW");
		con.rollback();
		
		//Test that retrieving invalid key returns null
		assertNull(repo.get(-99));
	}
}
