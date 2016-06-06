package com.mkjio.cleaner;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLCleaner {
    private static Pattern SCRIPTS = Pattern.compile("<script.*</script>");
    private static Pattern EXTERNAL_STYLES = Pattern.compile("background(-image)?:\\s?url[\\s]*\\([\\s]*(?<url>[^\\)]*)[\\s]*\\)[\\s]*;?");
    private static Pattern TAGS = Pattern.compile("<[^>]*>|<[^>]*>");
    private static Pattern STYLES = Pattern.compile("styles=\".*\"");
    private static Pattern ATTRIBUTES = Pattern.compile("[a-z]*=\".*\"");
    private static Pattern FULL = Pattern.compile("<script.*</script>|<[^>]*>");

    private String html;

    /**
     * Method removes script tags and all its content.
     *
     * Example:
     * Html before: <div><script>alert('hacked');</script></div>
     * After: <div></div>
     *
     * @return HTMLCleaner
     */
    public HTMLCleaner scripts() {
        Matcher match = SCRIPTS.matcher(html);
        html = match.replaceAll("");
        return this;
    }

    /**
     * Method removes external styles: background-image:url(''); background:url('');
     *
     * Example:
     * Html before: <div style="width:50px; background-image: url('https:google.com/picture.jpg');">Johny Rage</div>
     * After: <div style="width:50px;">Johny Rage</div>
     *
     * @return HTMLCleaner
     */

    public HTMLCleaner externalStyles() {
        Matcher match = EXTERNAL_STYLES.matcher(html);
        html = match.replaceAll("");
        return this;
    }

    /**
     * Method removes all tags (not only html) and attributes.
     *
     * Example:
     * Html before: <div style="width:50px">Johny Rage</div>
     * After: Johny Rage
     *
     * But be careful, if you have html like:
     * <div style="width:50px">
     *     <script>alert('some text')</script>
     *     Johny Rage
     * </div>
     * then after cleaning you get:
     * alert('some text')
     * Johny Rage.
     * Thus you should to remember, that this method is cleaning only tags and attributes.
     *
     * @return HTMLCleaner
     */

    public HTMLCleaner tags() {
        Matcher match = TAGS.matcher(html);
        html = match.replaceAll("");
        return this;
    }

    /**
     * Method removes all styles.
     *
     * Example:
     * Html before: <div style="width:50px; height:260px; color:red">Johny Rage</div>
     * After: <div>Johny Rage</div>
     *
     * @return HTMLCleaner
     */

    public HTMLCleaner styles() {
        Matcher match = STYLES.matcher(html);
        html = match.replaceAll("");
        return this;
    }

    /**
     * Method removes all attributes.
     *
     * Example:
     * Html before: <a href="#" class="btn">Johny Rage</div>
     * After: <div>Johny Rage</div>
     *
     * @return HTMLCleaner
     */
    public HTMLCleaner attributes() {
        Matcher match = ATTRIBUTES.matcher(html);
        html = match.replaceAll("");
        return this;
    }

    /**
     * Method removes all tags,scripts,attributes etc except text.
     *
     * Example:
     * Html before: <a href="#" class="btn">Johny Rage</div>
     * After: <div>Johny Rage</div>
     *
     * @return String
     */
    public String full() {
        if (isEmpty(html)) return html;
        Matcher match = FULL.matcher(html);
        html = match.replaceAll("");
        return finish();
    }

    public String finish() {
        if (isEmpty(html)) return html;
        return html.trim();
    }

    public HTMLCleaner clean(String html) {
        this.html = html;
        return this;
    }


    private boolean isEmpty(String str) {
        return str == null || str.isEmpty() || str.length() == 0;
    }

}
