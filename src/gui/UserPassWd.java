package gui;

/**
 * @author ed9e
 * @ClassName: Main
 * @Description: homework1_2.6
 * @date 2019/11/11
 * @Copyright
 */
import database.AccessDB;
public  class UserPassWd {
    static String create(String username, String passwd)
    {   if(username.equals("")||passwd.equals(""))
        {
            return "用户名或密码不能为空！";
        }
        else if(passwd.length()<6)
            return "密码不符合要求！";
        else
            return AccessDB.CreateUser(username,passwd);
    }
    static boolean Log(String username,String passwd)
    {
            return AccessDB.Login(username, passwd);
    }
}
