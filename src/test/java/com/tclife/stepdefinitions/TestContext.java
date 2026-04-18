package com.tclife.stepdefinitions;

import com.microsoft.playwright.Page;
import com.tclife.base.PlaywrightFactory;
import com.tclife.pages.InventoryPage;
import com.tclife.pages.LoginPage;

import java.util.Properties;

public class TestContext {
    public PlaywrightFactory pf;
    public Page page;
    public Properties prop;
    public LoginPage loginPage;
    public InventoryPage inventoryPage;
}
