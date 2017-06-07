package fzi.webtest.pages.artificial

import geb.Page


class BadContentPage extends Page {

   static url = '/'

   static content = {
      neverexistsUnspecified { $("#neverexists") }
      neverexistsRequired (required:true) { $("#neverexists") } // IS NOT EVALUATED by at check unless you reference it from "at closure"
      neverexistsUnrequired (required:false) { $("#neverexists") }
   }

   static at = { true }
}
