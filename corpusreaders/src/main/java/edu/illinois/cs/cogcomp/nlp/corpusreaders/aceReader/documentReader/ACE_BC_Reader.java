/**
 * This software is released under the University of Illinois/Research and Academic Use License. See
 * the LICENSE file in the root folder for details. Copyright (c) 2016
 *
 * Developed by: The Cognitive Computation Group University of Illinois at Urbana-Champaign
 * http://cogcomp.cs.illinois.edu/
 */
package edu.illinois.cs.cogcomp.nlp.corpusreaders.aceReader.documentReader;

import edu.illinois.cs.cogcomp.core.datastructures.Pair;
import edu.illinois.cs.cogcomp.nlp.corpusreaders.aceReader.Paragraph;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class ACE_BC_Reader {

    static boolean isDebug = false;

    public static List<Pair<String, Paragraph>> parse(String content, String contentRemovingTags) {
        List<Pair<String, Paragraph>> paragraphs = new ArrayList<Pair<String, Paragraph>>();

        Pattern pattern = null;
        Matcher matcher = null;

        String docID = "";
        String dateTime = "";
        String headLine = "";
        String text = "";

        pattern = Pattern.compile("<DOCID>(.*?)</DOCID>");
        matcher = pattern.matcher(content);
        while (matcher.find()) {
            docID = (matcher.group(1)).trim();
        }
        int index1 = content.indexOf(docID);
        Paragraph para1 = new Paragraph(index1, docID);
        Pair<String, Paragraph> pair1 = new Pair<String, Paragraph>("docID", para1);
        paragraphs.add(pair1);

        pattern = Pattern.compile("<DATETIME>(.*?)</DATETIME>");
        matcher = pattern.matcher(content);
        while (matcher.find()) {
            dateTime = (matcher.group(1)).trim();
        }
        int index2 = content.indexOf(dateTime);
        Paragraph para2 = new Paragraph(index2, dateTime);
        Pair<String, Paragraph> pair2 = new Pair<String, Paragraph>("dateTime", para2);
        paragraphs.add(pair2);

        pattern = Pattern.compile("<HEADLINE>(.*?)</HEADLINE>");
        matcher = pattern.matcher(content);
        while (matcher.find()) {
            headLine = (matcher.group(1)).trim();
        }
        int index3 = content.indexOf(headLine);
        Paragraph para3 = new Paragraph(index3, headLine);
        Pair<String, Paragraph> pair3 = new Pair<String, Paragraph>("headLine", para3);
        paragraphs.add(pair3);

        pattern = Pattern.compile("<TURN>(.*?)</TURN>");
        matcher = pattern.matcher(content);
        while (matcher.find()) {
            text = (matcher.group(1)).trim();

            text = text.substring(text.indexOf("</SPEAKER>") + "</SPEAKER>".length()).trim();
            int index4 = content.indexOf(text);
            Paragraph para4 = new Paragraph(index4, text);
            Pair<String, Paragraph> pair4 = new Pair<String, Paragraph>("text", para4);
            paragraphs.add(pair4);
        }

        int index = 0;
        for (int i = 0; i < paragraphs.size(); ++i) {
            int offsetWithFiltering =
                    contentRemovingTags.indexOf(paragraphs.get(i).getSecond().content, index);
            paragraphs.get(i).getSecond().offsetFilterTags = offsetWithFiltering;

            index += paragraphs.get(i).getSecond().content.length();
        }

        if (isDebug) {
            for (int i = 0; i < paragraphs.size(); ++i) {
                System.out.println(paragraphs.get(i).getFirst() + "--> "
                        + paragraphs.get(i).getSecond().content);
                System.out.println(content.substring(paragraphs.get(i).getSecond().offset,
                        paragraphs.get(i).getSecond().offset
                                + paragraphs.get(i).getSecond().content.length()));
                System.out.println(contentRemovingTags.substring(
                        paragraphs.get(i).getSecond().offsetFilterTags, paragraphs.get(i)
                                .getSecond().offsetFilterTags
                                + paragraphs.get(i).getSecond().content.length()));
                System.out.println();
            }
        }

        return paragraphs;
    }

}
