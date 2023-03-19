package Logic;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountTest {

    @Test
    @Order(1)
    void createAccount_emailAlreadyExists(){
        assertEquals(false, Account.createAccount("miguel@gmail.com", "miguel"));
    }

    @Test
    @Order(2)
    void createAccount_invalidEmail(){
        assertEquals(false, Account.createAccount("ines.gmail.com", "ines"));
    }

    @Test
    @Order(3)
    void createAccount_validParameters(){
        assertEquals(true, Account.createAccount("ines@gmail.com", "ines"));
    }

    @Test
    @Order(4)
    void LogOut_whenLoggedIn(){
        assertEquals(true, Account.LogOut());
    }

    @Test
    @Order(5)
    void deleteAccount_whenNotLoggedIn(){
        assertEquals(false, Account.deleteAccount("ines@gmail.com"));
    }

    @Test
    @Order(6)
    void LogIn_incorrectPassword(){
        assertEquals(false, Account.LogIn("ines@gmail.com", "carlota"));
    }

    @Test
    @Order(7)
    void LogIn_incorrectEmail(){
        assertEquals(false, Account.LogIn("carlota@gmail.com", "ines"));
    }

    @Test
    @Order(8)
    void LogIn_invalidEmail(){
        assertEquals(false, Account.LogIn("ines.gmail.com", "ines"));
    }

    @Test
    @Order(9)
    void LogOut_whenNotLoggedIn(){
        assertEquals(false, Account.LogOut());
    }

    @Test
    @Order(10)
    void changePassword_whenNotLoggedIn(){
        assertEquals(false, Account.changePassword("ines@gmail.com", "ines"));
    }

    @Test
    @Order(11)
    void LogIn_correctParameters(){
        assertEquals(true, Account.LogIn("ines@gmail.com", "ines"));
    }


    @Test
    @Order(12)
    void changePassword_whenLoggedIn(){
        assertEquals(true, Account.changePassword("ines@gmail.com", "ines"));
    }

    @Test
    @Order(13)
    void deleteAccount_whenLoggedIn(){
        assertEquals(true, Account.deleteAccount("ines@gmail.com"));
    }

    @Test
    @Order(14)
    void Login_afterDeletingAccount(){
        assertEquals(false, Account.LogIn("ines@gmail.com", "ines"));
    }

}