package seedu.address.logic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Scanner;
import java.util.Vector;

/**
 * Adds comments section.
 */
public class Comments {

    public static Vector v;
    public static String input;

    /**
     *  Appends comment to the end of the current Comment Section of index
     */
    public void addComment(String comment) {
        v.add(comment);
        rewrite(v,input);
    }

    /**
     *  Replies with the comment to event Comment section of index and line
     */
    public void replyComment(String comment, int index, int line) {

    }

    /**
     *  Admin only: Can delete comment given event Comment Section indexx and Line
     */
    public void deleteComment(int line) {
        v.remove(line);
        rewrite(v,input);
    }

    /**
     *  Admin only: Can delete comment given event Comment Section indexx and Line
     */
    public Vector parseCommentSection(String in) {
        String input = in;
        Vector v= new Vector();
        Document htmlfile=null;
        try {
            htmlfile = Jsoup.parse(new File(input),null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Element element = htmlfile.select("div").first();
        Elements divChildren = element.children();

        Elements detachedDivChildren = new Elements();
        for (Element elem : divChildren) {
            Element detachedChild = new Element(Tag.valueOf(elem.tagName()),
                    elem.baseUri(), elem.attributes().clone());
            detachedDivChildren.add(detachedChild);
        }
        for (Element elem : divChildren) {
            v.add(elem.ownText());
            System.out.println(elem.ownText());
        }
        return v;
    }

    /**
     *  Admin only: Can delete comment given event Comment Section indexx and Line
     */
    public static void rewrite(Vector v, String input) {
        String commentSection="<span>Comment Section</span><div>";
        for(int i=0;i<v.size();i++) {
            commentSection+="<p>"+v.get(i)+"</p>";
        }
        commentSection+="</div>";
        File savingFile = new File(input);
        FileOutputStream fop = null;
        try {
            fop = new FileOutputStream(savingFile);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            fop.write(commentSection.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            fop.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            fop.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Test code.
     */
    public static void main(String[] args) {
        Comments comment = new Comments();
        input = "C:/Users/Gerald/Desktop/test/1.html";
        Document htmlfile=null;
        try {
            htmlfile = Jsoup.parse(new File(input),null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        v = comment.parseCommentSection(input);

        while(true){
            System.out.println("What Comment do you want to add?");
            Scanner SCANNER = new Scanner(System.in);
            String username = SCANNER.nextLine();

            try {
                Integer.parseInt(username);
                comment.deleteComment(Integer.parseInt(username));
                System.out.println("Comment at " + username + "deleted");
            } catch(NumberFormatException e) {
                comment.addComment(username);
                System.out.println("Comment added!!");
            }




        }


    }
}
