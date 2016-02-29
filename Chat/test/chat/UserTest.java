/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ashour
 */
public class UserTest {
    
    public UserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of login method, of class User.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        User instance = null;
        Boolean expResult = true;
        Boolean result = instance.login();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class User.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        User instance = null;
        Boolean expResult = null;
        Boolean result = instance.add();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGender method, of class User.
     */
    @Test
    public void testGetGender() {
        System.out.println("getGender");
        User instance = null;
        String expResult = "";
        String result = instance.getGender();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEmail method, of class User.
     */
    @Test
    public void testGetEmail() {
        System.out.println("getEmail");
        User instance = null;
        String expResult = "";
        String result = instance.getEmail();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsername method, of class User.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        User instance = null;
        String expResult = "";
        String result = instance.getUsername();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStatus method, of class User.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        User instance = null;
        String expResult = "";
        String result = instance.getStatus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPassword method, of class User.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        User instance = null;
        String expResult = "";
        String result = instance.getPassword();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findUser method, of class User.
     */
    @Test
    public void testFindUser() {
        System.out.println("findUser");
        String searchEmail = "";
        User instance = null;
        User expResult = null;
        User result = instance.findUser(searchEmail);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of completeInfo method, of class User.
     */
    @Test
    public void testCompleteInfo() {
        System.out.println("completeInfo");
        User instance = null;
        User expResult = null;
        User result = instance.completeInfo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllUsers method, of class User.
     */
    @Test
    public void testGetAllUsers() {
        System.out.println("getAllUsers");
        String[] expResult = null;
        String[] result = User.getAllUsers();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContactList method, of class User.
     */
    @Test
    public void testGetContactList() {
        System.out.println("getContactList");
        String searchEmail = "";
        User instance = null;
        String[] expResult = null;
        String[] result = instance.getContactList(searchEmail);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isContact method, of class User.
     */
    @Test
    public void testIsContact() {
        System.out.println("isContact");
        String contactEmail = "";
        User instance = null;
        Boolean expResult = null;
        Boolean result = instance.isContact(contactEmail);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addContact method, of class User.
     */
    @Test
    public void testAddContact() {
        System.out.println("addContact");
        String contactEmail = "";
        User instance = null;
        Boolean expResult = null;
        Boolean result = instance.addContact(contactEmail);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isExist method, of class User.
     */
    @Test
    public void testIsExist() {
        System.out.println("isExist");
        String searchEmail = "";
        User instance = new User("ahmed", "123");
        Boolean expResult = false;
        Boolean result = instance.isExist(searchEmail);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
