package fzi.webtest.util



abstract class Behavior {

   static void sleepSoundly(int millis) {
      try {
         sleep(millis, {println 'interrupted. continue sleeping.';return false} )
      }
      catch ( InterruptedException e ) {
         println  'catched InterruptedException'
         Thread.currentThread().interrupt()
      }
   }
}

