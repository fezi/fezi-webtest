package fzi.webtest.modules

import geb.Module

// example for a module
class SidebarModule extends Module {

   static content = {
      sidebar { $(".wt-sidebar-area") }
      sidemenu { sidebar.find("ul.side-nav") }
      sidemenuAnchors { sidemenu.find("a") }
      activeSidemenuAnchor { sidemenu.find("li.active").find('a') }
   }
}
