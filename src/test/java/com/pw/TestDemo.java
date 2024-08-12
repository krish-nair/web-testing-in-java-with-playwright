package com.pw;

import org.junit.jupiter.api.Test;

public class TestDemo extends DemoBase{
    @Test
    public void downloadDemo(){
        theHerokuHomePage.setDownload();
    }
}
