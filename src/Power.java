
public class Power {
	public static double pow(double a, int b) {
	    if (b == 0){
	        return 1;
	    } else if (b == 1){
	        return a;
	    } else {
	        int mid = b/2;
	        double c = pow (a, mid); 
	        return c * c * (b %2 == 0 ? 1 : a) ;
	    
	    }
	    
	}
	
	public static String removeSpace(String str1){
		char[] str = str1.toCharArray();
		int x = 0;
		  for (int i=0;i<str.length;i++) {
			str[i-x]=str[i];
		    if (str[i]==' ') x++;
		  }
		  for (int j=str.length - x; j<str.length; j++){
			  str[j] = '\0';  
		  }
		  
		  StringBuilder myStr = new StringBuilder();
		  for (int i=0;i<str.length;i++) {
			  if (str[i] != '\0')
			 myStr.append(str[i]); 
		  }
		  return myStr.toString();
	}
	
	public static void permutation(String str) { 
	    permutation("", str); 
	}

	private static void permutation(String prefix, String str) {
		System.out.println("prefix="+prefix + " suffix="+str);
	    int n = str.length();

        for (int i = 0; i < n; i++){
        	 permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
        }

	}
	
	public static int change(int n, int[] coins){
		if (n < 0){
			return 10000;
		}
		for (int i=0; i<coins.length; i++){
			if (coins[i] == n){
				return 1;
			}
		}
		return 1 + Math.min(change(n-10, coins), Math.min(change(n-25, coins), change(n-1, coins))
					);	
	
	}
	
	public static void main(String[] args){
		System.out.println("This is nice.");
		System.out.println(removeSpace("This is nice."));
		
		System.out.println(pow(2,4));
		
		permutation("", "abc");
		
		int[] coins = {25,10,1};
		System.out.println(change(35, coins));
	}
}
