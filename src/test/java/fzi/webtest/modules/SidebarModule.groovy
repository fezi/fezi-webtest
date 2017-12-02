package fzi.webtest.modules

import geb.Module

// example for a module
class SidebarModule extends Module {

    static content = {
        sidebar { $("#toc") }
        sidemenuAnchors { sidebar.find("a") }
    }
}
