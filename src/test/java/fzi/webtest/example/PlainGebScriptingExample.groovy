package fzi.webtest.example

import fzi.webtest.util.Behavior
import geb.Browser

import org.testng.annotations.Test



/** example how to drive geb Browser by scripting groovy.*/
class PlainGebScriptingExample {

   @Test
   public void hijackVotingOfRockShop() {
      Browser.drive {
         go 'https://rockshop.de/RS/Band-Contest-2017/index.php'
         //go 'https://www.rockshop.de/rock-shop-news/band-contest-2017#Voting'
         def form = $('form', name: 'vote')
         form.find('span', text: 'Sons Of Sounds').click()
         form.find('input', type: 'submit').click()
         Behavior.sleepSoundly(3000)
         waitFor {$('strong').last().text().contains('Ihre Stimme wurde gez')}
      }
   }

   @Test
   public void testGoogleSearch() {
      Browser.drive {
         go "http://google.com/ncr"
         assert title == "Google"
         $("input", name: "q").value("wikipedia")
         waitFor { title.endsWith("Google Search") }
         def firstLink = $("li.g", 0).find("a.l")
         assert firstLink.text() == "Wikipedia"
         // seems to work even without 'extends GebReportingTest'. makes a png and html snapshot of current tab within the configured report dir.
         report('leavingGoogle')
         firstLink.click()
         waitFor { title.startsWith("Wikipedia") }
      }
   }


}
