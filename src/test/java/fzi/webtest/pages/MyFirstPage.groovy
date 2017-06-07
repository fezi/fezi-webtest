package fzi.webtest.pages

import geb.Page


class MyFirstPage extends Page {

   static url = '/manual/0.12.0/'

   static at = { browser.currentUrl.contains(url) }

   static content = {
      myElement { $("div#header") }
   }
}
