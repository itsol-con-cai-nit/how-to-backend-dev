package tips;

import java.util.Date;

// cách để giảm sự phức tạp của vòng if-else-for
public class Pyramids {
   int DoSomeThingSmell(int a, boolean b){
       if(a>5){
           if(b){
               if(a=10){
                   return -a;
               }else{
                   return 242;
               }
           }else{
               return -23;
           }
       }else{
           return 0;
       }
   }

   int DoSomeThingClean(int a, boolean b){
       if(a<=5){
           return 0;
       }

       if(!b){
           return -23;
       }

       if(a=10){
           return -a;
       }else{
           return 242;
       }
   }
