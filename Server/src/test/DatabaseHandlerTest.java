import com.example.semproj.DAO.DatabaseHandlerDAO;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseHandlerTest {

    @org.junit.jupiter.api.Test
    void getCountUsers() {
        int count=new DatabaseHandlerDAO().getCountUsers();
        int score = 6;
        assertEquals(score,count);
    }
}