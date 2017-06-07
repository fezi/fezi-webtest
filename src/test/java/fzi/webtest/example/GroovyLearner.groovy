package fzi.webtest.example

import org.testng.annotations.Test


class GroovyLearner {
   def foo
   String bar

   def doFoo(def foo) {
      def bar
   }

   boolean doBar() {
      bar
   }

   @Test
   void callWithList() {
      withList([1, 1, 2, 3, 5])
   }

   void withList(def list) {
      list.each { entry -> println "${entry}" }
      list.eachWithIndex { entry, index ->
         println "${index}: ${entry}"
      }
   }

   @Test
   void callWithMap() {
      withMap(test: '123', name: '324')
   }

   void withMap(def map) {
      map.each { key, value ->
         println "${key}: ${value}"
      }
   }

   @Test
   void callWithClosure() {
      assert withClosure {
         final boolean result = 1 == 1
         println "within closure ${result}"
         result
      }
   }

   def withClosure(Closure<Boolean> closure) {
      closure.call()
   }

   @Test
   void testAssert() {
      final String test = "test"
      assert "test" == test
   }

   void assertWithMessage() {
      assert false , "your message"
   }
}
