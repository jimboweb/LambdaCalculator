import java.util.ArrayList;
import java.util.List;
import java.lang.Character;
public class LamdaCalculator {

    public String applyFunction(String before, String after, String input){
        List<String> segments = stringToSegment(input);
        int[] beforeToAfterMap = mapBeforeToAfter(before, after);
        if(beforeToAfterMap.length>segments.size()){
            return ""; //sentinel that the before pattern's too long for the input
        }
        return placeSegments(segments,beforeToAfterMap);
    }

    private List<String> stringToSegment(String input){
        List<String> rtrn = new ArrayList<>();
        final int length = input.length();
        int parenDepth = 0;
        String currentString = "";
        for (int offset = 0; offset < length; offset++) {
            final int codepoint = input.charAt(offset);
            if(codepoint=='('){
                parenDepth++;
            } else if(codepoint==')'){
                parenDepth--;
            } else {
                currentString+=Character.toChars(codepoint);
            }
            if(parenDepth==0){
                rtrn.add(currentString);
                currentString="";
            } else if(parenDepth<0){
                throw new IllegalArgumentException("Too many closing parentheses.");
            }
        }
        if(parenDepth>0){
            throw new IllegalArgumentException("Too many opening parentheses.");
        }
        return rtrn;
    }

    /**
     * returns
     * @param before the before string of the function rule
     * @param after the after string of the function rule
     * @return int[] length of after not counting parens mapped to before positions
     */
    private int[] mapBeforeToAfter(String before, String after){
        int[] rtrn = new int[after.length()];
        for(int i=0;i<after.length();i++){
            char currentChar = after.charAt(i);
            if(currentChar=='('){
                rtrn[i]=-1;
            } else if(currentChar==')') {
                rtrn[i] = -2;
            } else {
                int beforePosition = before.indexOf(currentChar);
                if(beforePosition==-1){
                    throw new IllegalArgumentException("Character " + currentChar + " in after but not in before");
                }
                rtrn[i]=beforePosition;
            }

        }
        return rtrn;
    }

    private String placeSegments(List<String> segments, int[] beforeToAfterMap){
        String rtrn = "";
        for(int i:beforeToAfterMap){
            if(segments.size()<i){
                throw new IllegalArgumentException("no segment matches map position " + i);
            }
            rtrn += segments.get(i);
        }
        return rtrn;
    }
}
