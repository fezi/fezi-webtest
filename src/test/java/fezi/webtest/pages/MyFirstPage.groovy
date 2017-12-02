package fezi.webtest.pages

import fezi.webtest.modules.SidebarModule
import geb.Page


class MyFirstPage extends Page {

    static url = '/manual/2.0/'

    static at = { browser.currentUrl.contains(url) && headline.displayed }

    static content = {
        headline { $("div#header h1") }
        sidebar { module SidebarModule }
    }
}
