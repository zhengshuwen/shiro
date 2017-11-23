package sys.springmvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Utils {
	
	
	
	
	 public int[] folding(int[] nums, int[] req) {
		 List<int[]> list=new ArrayList<int[]>();
		 List<int[]> copylist=new ArrayList<int[]>();
		 list.add(nums);
		 for(int j:req){
			 for(int[] num:list){
				 int length=num.length;
				 int[] newNums1=Arrays.copyOfRange(num, 0, length/2);
				 int[] newNums2=Arrays.copyOfRange(num, length/2,length);
				 if(j==0){
					 int[] copyArray=Arrays.copyOf(newNums1, length/2);
					 for(int c=0; c<newNums1.length;c++){
						 newNums1[c]=copyArray[newNums1.length-1-c];
					 }
					 copylist.add(newNums2);
					 copylist.add(0, newNums1);
				 }else if(j==1){
					 int[] copyArray=Arrays.copyOf(newNums2, length/2);
					 for(int c=0; c<newNums2.length;c++){
						 newNums2[c]=copyArray[newNums2.length-1-c];
					 }
					 copylist.add(0, newNums2);
					 copylist.add(newNums1);
				 } 
			 }
			 list.clear();
			 list.addAll(copylist);
			 copylist.clear();
		 }
		 int[] returnNum=new int[list.size()];
		 int k=0;
		 for(int[] value:list){
			 returnNum[k]=value[0];
			 k++;
		 }
		 return returnNum;
	 }
	 
	 @Test
	 public void test(){
		 int[] nums={1,2,3,4,5,6,7,8};
		 int[] req={0,0,1};
		 folding(nums,req);
	 }
}
