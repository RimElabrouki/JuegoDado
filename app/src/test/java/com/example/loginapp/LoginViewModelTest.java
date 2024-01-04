package com.example.loginapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.example.loginapp.ui.login.LoginViewModel;

import org.junit.Before;
import org.junit.Test;

public class LoginViewModelTest {
    private LoginViewModel loginViewModel ;


    @Before
    public void instanciarLoginViewModel(){
        LoginViewModel loginViewModel = new LoginViewModel();

    }
    /* compruebe si el parámetro que le paso es nulo o no. Devuelve
 True si no el nullo y falso si el nulo. Es decir, compruebo si
 existe el objeto LoginViewModel, ya que lo voy a utilizar en los
 siguientes @Test */
    @Test
    public void miOperacionNoNula(){
        assertNotNull(loginViewModel);
    }
//extra caracter
    @Test
    public void isUserNameValid() {
        LoginViewModel loginViewModel = new LoginViewModel();
        assertFalse(loginViewModel.isUserNameValid("name@email..com"));
    }
    //null
    @Test
    public void isUserNameValidNull() {
        LoginViewModel loginViewModel = new LoginViewModel();
        assertFalse(loginViewModel.isUserNameValid(""));
    }


}
