import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Calculator {
    public HashMap<Integer,String> id_key;
    public ArrayList<Double> moneys;
    public int target;
    public HashMap<Double,Integer> id_re;
    public double min_val=Double.MIN_VALUE;
    public double sum=0.0;
    public ArrayList<Double> path;
    public ArrayList<Double> ans;
    public int flag=0;
    public Calculator(HashMap<Integer,String> ik,ArrayList<Double> m,int target){
        this.id_key=ik;
        this.moneys=m;
        this.target=target;
    }
    /*
   将存储的数据进行最接近1000的计算，并将其索引进行保存
    */
    public void dfs(int i,int j,ArrayList<Double> nums,int target){
        if (nums.get(i)>target){
            if(sum>min_val){
                ans.clear();
                for(Double val:path){ans.add(val);}
                min_val=sum;
            }
            return;
        }
        if (flag==1) return;
        path.add(nums.get(i));
        sum+=nums.get(i);
        if(sum>target){
            if(sum-nums.get(i)>min_val){
                path.remove(path.size()-1);
                for(Double val:path){ans.add(val);}
                min_val=sum-nums.get(i);
            }else{
                path.remove(path.size()-1);
            }
            sum-=nums.get(i);
            return;
        }else if (sum==target){
            for(Double val:path){ans.add(val);}
            flag=1;
            return;
        }
        for(int k=i+1;k<nums.size();k++){
            dfs(k,j,nums,target);
        }
        if (i==nums.size()-1&&sum<target){
            if(sum>min_val){
                ans.clear();
                for(Double val:path){ans.add(val);}
                min_val=sum;
            }
        }
        sum-=nums.get(i);
        path.remove(nums.get(i));
    }

    public ArrayList<Integer> calculator(){
        id_re=new HashMap<>();
        path=new ArrayList<>();
        ans=new ArrayList<>();
        for(int i=0;i<moneys.size();i++){
            id_re.put(moneys.get(i),i);
        }
        Collections.sort(moneys);
        for(int i=0;i<moneys.size();i++){
            if(flag==1) break;
            if(moneys.get(i)<target){
                dfs(i,i,moneys,target);
            }
        }
        ArrayList<Integer> indexs=new ArrayList<>();
        for (int i=0;i<ans.size();i++){
            indexs.add(id_re.get(ans.get(i)));
        }
        return indexs;
    }
}
