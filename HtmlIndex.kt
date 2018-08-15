package com.qamar.lang.html

import android.content.Context
import android.graphics.Color.BLUE
import android.text.SpannableStringBuilder
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.view.View
import com.qamar.app.R
import com.qamar.app.core.AppContext
import com.qamar.app.util.getDrawable
import com.qamar.lang.Completion
import com.qamar.lang.DefaultCompletionView

/*
 * Html elements and attributes licensed under CC-BY-SA 2.5 (https://creativecommons.org/licenses/by-sa/2.5/)
 * by MDN.
 * Originally from https://developer.mozilla.org/en-US/ (but slightly modified)
 */

/**
 * A html element
 */
class HtmlElement(override val name: String, override val description: CharSequence,
                       val deprecated: Boolean = false) : Completion {

    val attributes: List<HtmlAttribute> by lazy {
        val list = ArrayList<HtmlAttribute>()
        list.addAll(GLOBAL_ATTRIBUTES)
        list.addAll(HTML_ATTRIBUTES.filter { it.elements.contains(this) })
        list
    }

    override fun equals(other: Any?): Boolean {
        return name == (other as? HtmlElement)?.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun asView(appContext: AppContext, convertView: View?): View {
        val context = appContext.androidContext

        val text = SpannableStringBuilder(name)
        if (deprecated)
            text.setSpan(StrikethroughSpan(), 0, text.length, SPAN_EXCLUSIVE_EXCLUSIVE)

        return if (convertView is HtmlCompletionView) {
            convertView.icon = getDrawable(context, R.drawable.ic_element_48dp)
            convertView.label = text

            convertView
        }
        else {
            val completionView = HtmlCompletionView(context)
            completionView.icon = getDrawable(context, R.drawable.ic_element_48dp)
            completionView.label = text

            completionView
        }
    }
}

/**
 * A html attribute.
 * If it has empty elements it means it is global.
 */
data class HtmlAttribute(override val name: String, override val description: CharSequence?,
                       val deprecated: Boolean = false, val elements: List<HtmlElement> = emptyList()) : Completion {

    fun isGlobal() = elements.isEmpty()

    override fun asView(appContext: AppContext, convertView: View?): View {
        val context = appContext.androidContext

        val text = SpannableStringBuilder(name)

        if (deprecated)
            text.setSpan(StrikethroughSpan(), 0, text.length, SPAN_EXCLUSIVE_EXCLUSIVE)

        if (isGlobal())
            text.setSpan(ForegroundColorSpan(BLUE), 0, text.length, SPAN_EXCLUSIVE_EXCLUSIVE)

        return if (convertView is HtmlCompletionView) {
            convertView.icon = getDrawable(context, R.drawable.ic_attribute_48dp)
            convertView.label = text

            convertView
        }
        else {
            val completionView = HtmlCompletionView(context)
            completionView.icon = getDrawable(context, R.drawable.ic_attribute_48dp)
            completionView.label = text

            completionView
        }
    }
}

private class HtmlCompletionView(context: Context)
    : DefaultCompletionView(context)

val HTML_ELEMENTS: List<HtmlElement> by lazy {
    listOf(ELEMENT_HTML, ELEMENT_LINK, ELEMENT_META, ELEMENT_STYLE, ELEMENT_TITLE, ELEMENT_BODY,
            ELEMENT_ADDRESS, ELEMENT_ARTICLE, ELEMENT_ASIDE, ELEMENT_FOOTER, ELEMENT_HEAD,
            ELEMENT_HEADER, ELEMENT_H1, ELEMENT_H2, ELEMENT_H3, ELEMENT_H4, ELEMENT_H5, ELEMENT_H6,
            ELEMENT_HGROUP, ELEMENT_NAV, ELEMENT_SECTION, ELEMENT_BLOCKQUOTE, ELEMENT_DD,
            ELEMENT_DIR, ELEMENT_DIV, ELEMENT_DL, ELEMENT_DT, ELEMENT_FIGCAPTION, ELEMENT_FIGURE,
            ElEMENT_HR, ElEMENT_LI, ELEMENT_MAIN, ELEMENT_OL, ELEMENT_P, ELEMENT_PRE, ELEMENT_UL,
            ELEMENT_A, ELEMENT_ABBR, ELEMENT_B, ELEMENT_BDI, ELEMENT_BDO, ELEMENT_BR, ELEMENT_CITE,
            ELEMENT_CODE, ELEMENT_DATA, ELEMENT_DFN, ELEMENT_EM, ELEMENT_I, ELEMENT_KBD,
            ELEMENT_MARK, ELEMENT_Q, ELEMENT_RB, ELEMENT_RP, ELEMENT_RT, ELEMENT_RTC, ELEMENT_RUBY,
            ELEMENT_S, ELEMENT_SAMP, ELEMENT_SMALL, ELEMENT_SPAN, ELEMENT_STRONG, ELEMENT_SUB,
            ELEMENT_SUP, ELEMENT_TIME, ELEMENT_TT, ELEMENT_U, ELEMENT_VAR, ELEMENT_WBR, ELEMENT_AREA,
            ELEMENT_AUDIO, ELEMENT_IMG, ELEMENT_MAP, ELEMENT_VIDEO, ELEMENT_APPLET, ELEMENT_EMBED,
            ELEMENT_IFRAME, ELEMENT_NOEMBED, ELEMENT_OBJECT, ELEMENT_PARAM, ELEMENT_PICTURE,
            ELEMENT_SOURCE, ELEMENT_CANVAS, ELEMENT_NOSCRIPT, ELEMENT_SCRIPT, ELEMENT_DEL,
            ELEMENT_INS, ELEMENT_CAPTION, ELEMENT_COL, ELEMENT_COLGROUP, ELEMENT_TABLE,
            ELEMENT_TBODY, ELEMENT_TD, ELEMENT_TFOOT, ELEMENT_TH, ELEMENT_THEAD, ELEMENT_TR,
            ELEMENT_BUTTON, ELEMENT_DATALIST, ELEMENT_FIELDSET, ELEMENT_FORM, ELEMENT_INPUT,
            ELEMENT_LABEL, ELEMENT_LEGEND, ELEMENT_METER, ELEMENT_OPTGROUP, ELEMENT_OPTION,
            ELEMENT_OUTPUT, ELEMENT_PROGRESS, ELEMENT_SELECT, ELEMENT_TEXTAREA, ELEMENT_DETAILS,
            ELEMENT_DIALOG, ELEMENT_MENU, ELEMENT_MENUITEM, ELEMENT_SUMMARY, ELEMENT_CONTENT,
            ELEMENT_ELEMENT, ELEMENT_SHADOW, ELEMENT_SLOT, ELEMENT_TEMPLATE)
}

val HTML_ATTRIBUTES: List<HtmlAttribute> by lazy {
    listOf(ATTRIBUTE_ACCEPT, ATTRIBUTE_ACCEPT_CHARSET, ATTRIBUTE_ACCESSKEY, ATTRIBUTE_ACTION,
            ATTRIBUTE_ALLIGN, ATTRIBUTE_ALT, ATTRIBUTE_ASYNC, ATTRIBUTE_AUTOCAPITALIZE,
            ATTRIBUTE_AUTOCOMPLETE, ATTRIBUTE_AUTOFOCUS, ATTRIBUTE_AUTOPLAY, ATTRIBUTE_BGCOLOR,
            ATTRIBUTE_BORDER, ATTRIBUTE_BUFFERED, ATTRIBUTE_CHARSET,
            ATTRIBUTE_CHECKED, ATTRIBUTE_CITE, ATTRIBUTE_CLASS, ATTRIBUTE_CODE, ATTRIBUTE_CODEBASE,
            ATTRIBUTE_COLOR, ATTRIBUTE_COLS, ATTRIBUTE_COLSPAN, ATTRIBUTE_CONTENT,
            ATTRIBUTE_CONTENTEDITABLE, ATTRIBUTE_CONTEXTMENU, ATTRIBUTE_CONTROLS, ATTRIBUTE_COORDS,
            ATTRIBUTE_CROSSORIGIN, ATTRIBUTE_DATA, ATTRIBUTE_DATETIME, ATTRIBUTE_DEFER, ATTRIBUTE_DIR,
            ATTRIBUTE_DIRNAME, ATTRIBUTE_DISABLED, ATTRIBUTE_DOWNLOAD, ATTRIBUTE_DRAGGABLE,
            ATTRIBUTE_DROPZONE, ATTRIBUTE_ENCTYPE, ATTRIBUTE_FOR, ATTRIBUTE_FORM, ATTRIBUTE_FORMACTION,
            ATTRIBUTE_HEADERS, ATTRIBUTE_HEIGHT, ATTRIBUTE_HIDDEN, ATTRIBUTE_HIGH, ATTRIBUTE_HREF,
            ATTRIBUTE_HREFLANG, ATTRIBUTE_HTTP_EQUIV, ATTRIBUTE_ID, ATTRIBUTE_INTEGRITY,
            ATTRIBUTE_ISMAP, ATTRIBUTE_ITEMPROP, ATTRIBUTE_LANG, ATTRIBUTE_LANGUAGE,
            ATTRIBUTE_LIST, ATTRIBUTE_LOOP, ATTRIBUTE_LOW, ATTRIBUTE_MANIFEST, ATTRIBUTE_MAX,
            ATTRIBUTE_MAXLENGTH, ATTRIBUTE_MINLENGTH, ATTRIBUTE_MEDIA, ATTRIBUTE_METHOD, ATTRIBUTE_MIN,
            ATTRIBUTE_MULTIPLE, ATTRIBUTE_MUTED, ATTRIBUTE_NAME, ATTRIBUTE_NOVALIDATE, ATTRIBUTE_OPEN,
            ATTRIBUTE_OPTIMUM, ATTRIBUTE_PATTERN, ATTRIBUTE_PING, ATTRIBUTE_PLACEHOLDER, ATTRIBUTE_POSTER,
            ATTRIBUTE_PRELOAD, ATTRIBUTE_READONLY, ATTRIBUTE_REL, ATTRIBUTE_REQUIRED,
            ATTRIBUTE_REVERSED, ATTRIBUTE_ROWS, ATTRIBUTE_ROWSPAN, ATTRIBUTE_SANDBOX, ATTRIBUTE_SCOPE,
            ATTRIBUTE_SCOPED, ATTRIBUTE_SELECTED, ATTRIBUTE_SHAPE, ATTRIBUTE_SIZE, ATTRIBUTE_SIZES,
            ATTRIBUTE_SLOT, ATTRIBUTE_SPAN, ATTRIBUTE_SPELLCHECK, ATTRIBUTE_SRC, ATTRIBUTE_SRCDOC,
            ATTRIBUTE_SRCSET, ATTRIBUTE_START, ATTRIBUTE_STEP, ATTRIBUTE_STYLE,
            ATTRIBUTE_SUMMARY, ATTRIBUTE_TABINDEX, ATTRIBUTE_TARGET, ATTRIBUTE_TITLE, ATTRIBUTE_TRANSLATE,
            ATTRIBUTE_TYPE, ATTRIBUTE_USEMAP, ATTRIBUTE_VALUE, ATTRIBUTE_WIDTH, ATTRIBUTE_WRAP
            )
}

val GLOBAL_ATTRIBUTES: List<HtmlAttribute> by lazy {
    HTML_ATTRIBUTES.filter { it.isGlobal() }
}


/*******************************************************************************************
 *********************************    Main root   ******************************************
 ********************************************************************************************/

val ELEMENT_HTML = HtmlElement("html", "Represents the root (top-level element) of " +
            "an HTML document, so it is also referred to as the root element. All other elements must be " +
            "descendants of this element.")

/*******************************************************************************************
 *********************************    Document metadata   **********************************
 *******************************************************************************************/

val ELEMENT_LINK = HtmlElement("link", "Specifies relationships between the current " +
            "document and an external resource. This element is most commonly used to link to stylesheets.")

val ELEMENT_META = HtmlElement("meta", "Represents metadata that cannot be " +
            "represented by other HTML meta-related elements, like <base>, <link>, <script>, <style> " +
            "or <title>.")

val ELEMENT_STYLE = HtmlElement("style", "Contains style information for a document," +
            " or part of a document.")

val ELEMENT_TITLE = HtmlElement("title", "Defines the document's title that is shown" +
            " in a browser's title bar or a page's tab.")


/*******************************************************************************************
 *********************************    Sectioning root   ************************************
 *******************************************************************************************/

val ELEMENT_BODY = HtmlElement("body", "Represents the content of an HTML document.\n" +
            "There can be only one <body> element in a document.")

/*******************************************************************************************
 *********************************    Content sectioning   *********************************
 *******************************************************************************************/

val ELEMENT_ADDRESS = HtmlElement("address", "Indicates that the enclosed HTML " +
            "provides contact information for a person or people, or for an organization.")

val ELEMENT_ARTICLE = HtmlElement("article", "Represents a self-contained " +
            "composition in a document, page, application, or site, which is intended to be independently " +
            "distributable or reusable (e.g., in syndication).\n" +
            "Examples include: a forum post, a magazine or newspaper article, or a blog entry.")

val ELEMENT_ASIDE = HtmlElement("aside", "Represents a portion of a document whose " +
            "content is only indirectly related to the document's main content.")

val ELEMENT_FOOTER = HtmlElement("footer", "Represents a footer for its nearest " +
            "sectioning content or sectioning root element.\n" +
            "A footer typically contains information about the author of the section, copyright data " +
            "or links to related documents.")

val ELEMENT_HEAD = HtmlElement("head", "Provides general information (metadata) about the document, " +
        "including its title and links to its scripts and style sheets.")

val ELEMENT_HEADER = HtmlElement("header", "Represents introductory content, " +
            "typically a group of introductory or navigational aids.\n" +
            "It may contain some heading elements but also other elements like a logo, a search form, " +
            "an author name, and so on.")

val ELEMENT_H1 = HtmlElement("h1", "The HTML <h1>–<h6> elements represent six " +
            "levels of section headings.\n" +
            "<h1> is the highest section level and <h6> is the lowest.")

val ELEMENT_H2 = HtmlElement("h2", "The HTML <h1>–<h6> elements represent six " +
            "levels of section headings.\n" +
            "<h1> is the highest section level and <h6> is the lowest.")

val ELEMENT_H3 = HtmlElement("h3", "The HTML <h1>–<h6> elements represent six " +
            "levels of section headings.\n" +
            "<h1> is the highest section level and <h6> is the lowest.")

val ELEMENT_H4 = HtmlElement("h4", "The HTML <h1>–<h6> elements represent six " +
            "levels of section headings.\n" +
            "<h1> is the highest section level and <h6> is the lowest.")

val ELEMENT_H5 = HtmlElement("h5", "The HTML <h1>–<h6> elements represent six " +
            "levels of section headings.\n" +
            "<h1> is the highest section level and <h6> is the lowest.")

val ELEMENT_H6 = HtmlElement("h6", "The HTML <h1>–<h6> elements represent six " +
            "levels of section headings.\n" +
            "<h1> is the highest section level and <h6> is the lowest.")

val ELEMENT_HGROUP = HtmlElement("hgroup", "Represents a multi-level heading for " +
            "a section of a document.\n" +
            "It groups a set of <h1>–<h6> elements.")

val ELEMENT_NAV = HtmlElement("nav", "Represents a section of a page whose purpose " +
            "is to provide navigation links, either within the current document or to other documents.\n" +
            "Common examples of navigation sections are menus, tables of contents, and indexes.")

val ELEMENT_SECTION = HtmlElement("section", "Represents a standalone section — " +
            "which doesn't have a more specific semantic element to represent it — contained within an " +
            "HTML document.")

/*******************************************************************************************
 ************************************     Text content   ************************************
 *******************************************************************************************/

val ELEMENT_BLOCKQUOTE = HtmlElement("blockquote", "Indicates that the enclosed " +
            "text is an extended quotation.\n" +
            "Usually, this is rendered visually by indentation.\n" +
            "A URL for the source of the quotation may be given using the cite attribute, while a " +
            "text representation of the source can be given using the <cite> element.")

val ELEMENT_DD = HtmlElement("dd", "Provides the details about or the definition of " +
            "the preceding term (<dt>) in a description list (<dl>).")

val ELEMENT_DIR = HtmlElement("dir", "The obsolete HTML Directory element" +
            "is used as a container for a directory of files and/or folders, potentially with styles " +
            "and icons applied by the user agent.", true)

val ELEMENT_DIV = HtmlElement("div", "The HTML Content Division element is the " +
            "generic container for flow content.\n" +
            "It has no effect on the content or layout until styled using CSS.")

val ELEMENT_DL = HtmlElement("dl", "Represents a description list.\n" +
            "The element encloses a list of groups of terms (specified using the <dt> element) and " +
            "descriptions (provided by <dd> elements).\n" +
            "Common uses for this element are to implement a glossary or to display metadata " +
            "(a list of key-value pairs).")

val ELEMENT_DT = HtmlElement("dt", "Specifies a term in a description or definition " +
            "list, and as such must be used inside a <dl> element.")

val ELEMENT_FIGCAPTION = HtmlElement("figcaption", "Represents a caption or a " +
            "legend associated with a figure or an illustration described by the rest of the data of " +
            "the <figure> element which is its immediate ancestor.")

val ELEMENT_FIGURE = HtmlElement("figure", "Represents self-contained content, " +
            "frequently with a caption (<figcaption>), and is typically referenced as a single unit.")

val ElEMENT_HR = HtmlElement("hr", "Represents a thematic break between " +
            "paragraph-level elements (for example, a change of scene in a story, or a shift of topic " +
            "with a section); historically, this has been presented as a horizontal rule or line.")

val ElEMENT_LI = HtmlElement("li", "Represents an item in a list.\n" +
            "It must be contained in a parent element: an ordered list (<ol>), an unordered list (<ul>), " +
            "or a menu (<menu>).\n" +
            "In menus and unordered lists, list items are usually displayed using bullet points.\n" +
            "In ordered lists, they are usually displayed with an ascending counter on the left, such " +
            "as a number or letter.")

val ELEMENT_MAIN = HtmlElement("main", "Represents the dominant content of the " +
            "<body> of a document, portion of a document or application.\n" +
            "The main content area consists of content that is directly related to or expands upon the " +
            "central topic of a document, or the central functionality of an application.")

val ELEMENT_OL = HtmlElement("ol", "Represents an ordered list of items, typically " +
            "rendered as a numbered list.")

val ELEMENT_P = HtmlElement("p", "Represents a paragraph of text.")

val ELEMENT_PRE = HtmlElement("pre", "Represents preformatted text which is to be " +
            "presented exactly as written in the HTML file.")

val ELEMENT_UL = HtmlElement("ul", "Represents an unordered list of items, typically " +
            "rendered as a bulleted list.")


/*******************************************************************************************
 ********************************     Inline text semantics   ******************************
 *******************************************************************************************/


val ELEMENT_A = HtmlElement("a", "Creates a hyperlink to other web pages, files, " +
            "locations within the same page, email addresses, or any other URL.")

val ELEMENT_ABBR = HtmlElement("abbr", "Represents an abbreviation or acronym; the " +
            "optional title attribute can provide an expansion or description for the abbreviation.")

val ELEMENT_B = HtmlElement("b", "Used to draw the reader's attention to the " +
            "element's contents, which are not otherwise granted special importance.")

val ELEMENT_BDI = HtmlElement("bdi", "Used to indicate spans of text which might " +
            "need to be rendered in the opposite direction than the surrounding text.")

val ELEMENT_BDO = HtmlElement("bdo", "Overrides the current directionality of text, " +
            "so that the text within is rendered in a different direction.")

val ELEMENT_BR = HtmlElement("br", "Produces a line break in text (carriage-return).\n" +
            "It is useful for writing a poem or an address, where the division of lines is significant.")

val ELEMENT_CITE = HtmlElement("cite", "Used to describe a reference to a cited " +
            "creative work, and must include either the title or the URL of that work.")

val ELEMENT_CODE = HtmlElement("code", "Displays its contents styled in a fashion " +
            "intended to indicate that the text is a short fragment of computer code.")

val ELEMENT_DATA = HtmlElement("data", "Links a given content with a " +
            "machine-readable translation.\n" +
            "If the content is time- or date-related, the <time> element must be used.")

val ELEMENT_DFN = HtmlElement("dfn", "Used to indicate the term being defined " +
            "within the context of a definition phrase or sentence.")

val ELEMENT_EM = HtmlElement("em", "Marks text that has stress emphasis.\n" +
            "The <em> element can be nested, with each level of nesting indicating a greater degree " +
            "of emphasis.")

val ELEMENT_I = HtmlElement("i", "Represents a range of text that is set off from " +
            "the normal text for some reason.\n" +
            "Some examples include technical terms or foreign language phrases.\n" +
            "It is typically displayed in italic type.")

val ELEMENT_KBD = HtmlElement("kbd", "Represents a span of inline text denoting " +
            "textual user input from a keyboard, voice input, or any other text entry device.")

val ELEMENT_MARK = HtmlElement("mark", "Represents text which is marked or " +
            "highlighted for reference or notation purposes, due to the marked passage's relevance or " +
            "importance in the enclosing context.")

val ELEMENT_Q = HtmlElement("q", "Indicates that the enclosed text is a short " +
            "inline quotation.\n" +
            "Most modern browsers implement this by surrounding the text in quotation marks.")

val ELEMENT_RB = HtmlElement("rb", "Used to delimit the base text component of a  " +
            "<ruby> annotation, i.e. the text that is being annotated.")

val ELEMENT_RP = HtmlElement("rp", "Used to provide fall-back parentheses for " +
            "browsers that do not support display of ruby annotations using the <ruby> element.")

val ELEMENT_RT = HtmlElement("rt", "Specifies the ruby text component of a ruby " +
            "annotation, which is used to provide pronunciation, translation, or transliteration " +
            "information for East Asian typography.\n" +
            "The <rt> element must always be contained within a <ruby> element.")

val ELEMENT_RTC = HtmlElement("rtc", "Embraces semantic annotations of characters " +
            "presented in a ruby of <rb> elements used inside of <ruby> element.\n" +
            "<rb> elements can have both pronunciation (<rt>) and semantic (<rtc>) annotations.\n")

val ELEMENT_RUBY = HtmlElement("ruby", "Represents a ruby annotation.\n" +
            "Ruby annotations are for showing pronunciation of East Asian characters.")

val ELEMENT_S = HtmlElement("s", "Renders text with a strikethrough, or a line " +
            "through it.\n" +
            "Use the <s> element to represent things that are no longer relevant or no longer accurate.\n" +
            "However, <s> is not appropriate when indicating document edits; for that, use the <del> " +
            "and <ins> elements, as appropriate.")

val ELEMENT_SAMP = HtmlElement("samp", "Used to enclose inline text which " +
            "represents sample (or quoted) output from a computer program.")

val ELEMENT_SMALL = HtmlElement("small", "Makes the text font size one size smaller " +
            "(for example, from large to medium, or from small to x-small) down to the browser's " +
            "minimum font size.\n" +
            "In HTML5, this element is repurposed to represent side-comments and small print, including\n" +
            "copyright and legal text, independent of its styled presentation.")

val ELEMENT_SPAN = HtmlElement("span", "A generic inline container for phrasing " +
            "content, which does not inherently represent anything.\n" +
            "It can be used to group elements for styling purposes (using the class or id attributes), " +
            "or because they share attribute values, such as lang.")

val ELEMENT_STRONG = HtmlElement("strong", "Indicates that its contents have strong " +
            "importance, seriousness, or urgency.\n" +
            "Browsers typically render the contents in bold type.")

val ELEMENT_SUB = HtmlElement("sub", "Specifies inline text which should be " +
            "displayed as subscript for solely typographical reasons.")

val ELEMENT_SUP = HtmlElement("sup", "Specifies inline text which is to be " +
            "displayed as superscript for solely typographical reasons.")

val ELEMENT_TIME = HtmlElement("time", "Represents a specific period in time.\n" +
            "It may include the datetime attribute to translate dates into machine-readable format, " +
            "allowing for better search engine results or custom features such as reminders.")

val ELEMENT_TT = HtmlElement("tt", "The obsolete Teletype Text element creates " +
            "inline text which is presented using the user agent's default monospace font face.", true)

val ELEMENT_U = HtmlElement("u", "Represents a span of inline text which should be " +
            "rendered in a way that indicates that it has a non-textual annotation.")

val ELEMENT_VAR = HtmlElement("var", "Represents the name of a variable in a " +
            "mathematical expression or a programming context.")

val ELEMENT_WBR = HtmlElement("wbr", "Represents a word break opportunity — a " +
            "position within text where the browser may optionally break a line, though its " +
            "line-breaking rules would not otherwise create a break at that location.")


/*******************************************************************************************
 *****************************    Image and multimedia   ***********************************
 *******************************************************************************************/

val ELEMENT_AREA = HtmlElement("area", "Defines a hot-spot region on an image, and " +
            "optionally associates it with a hypertext link.\n" +
            "This element is used only within a <map> element.")

val ELEMENT_AUDIO = HtmlElement("audio", "Used to embed sound content in documents." +
            "It may contain one or more audio sources, represented using the src attribute or the " +
            "<source> element: the browser will choose the most suitable one.\n" +
            "It can also be the destination for streamed media, using a MediaStream.")

val ELEMENT_IMG = HtmlElement("img", "Embeds an image into the document.")

val ELEMENT_MAP = HtmlElement("map", "Used with <area> elements to define an " +
            "image map (a clickable link area).")

val ELEMENT_VIDEO = HtmlElement("video", "Embeds a media player which supports video playback into the document.")


/*******************************************************************************************
 *********************************    Embedded content   ***********************************
 *******************************************************************************************/

val ELEMENT_APPLET = HtmlElement("applet", "Embeds a Java applet into the document; this " +
            "element has been deprecated in favor of <object>.", true)

val ELEMENT_EMBED = HtmlElement("embed", "Embeds external content at the specified " +
            "point in the document.\n" +
            "This content is provided by an external application or other source of interactive " +
            "content such as a browser plug-in.")

val ELEMENT_IFRAME = HtmlElement("iframe", "Represents a nested browsing context, " +
            "effectively embedding another HTML page into the current page.")

val ELEMENT_NOEMBED = HtmlElement("noembed", "An obsolete, non-standard way to " +
            "provide alternative, or \"fallback\", content for browsers that do not support the " +
            "<embed> element or do not support the type of embedded content an author wishes to use.", true)

val ELEMENT_OBJECT = HtmlElement("object", "Represents an external resource, which " +
            "can be treated as an image, a nested browsing context, or a resource to be handled by a plugin.")

val ELEMENT_PARAM = HtmlElement("param", "Defines parameters for an <object> element.")

val ELEMENT_PICTURE = HtmlElement("picture", "Serves as a container for zero or " +
            "more <source> elements and one <img> element to provide versions of an image for different " +
            "display device scenarios.")

val ELEMENT_SOURCE = HtmlElement("source", "Specifies multiple media resources for " +
            "the <picture>, the <audio> element, or the <video> element.\n" +
            "It is an empty element.\n" +
            "It is commonly used to serve the same media content in multiple formats supported by " +
            "different browsers.")


/*******************************************************************************************
 *************************************    Scripting   **************************************
 *******************************************************************************************/


val ELEMENT_CANVAS = HtmlElement("canvas", "Use the <canvas> element with either " +
            "the canvas scripting API or the WebGL API to draw graphics and animations.")

val ELEMENT_NOSCRIPT = HtmlElement("noscript", "Defines a section of HTML to be " +
            "inserted if a script type on the page is unsupported or if scripting is currently turned " +
            "off in the browser.")

val ELEMENT_SCRIPT = HtmlElement("script", "The <script> element is used to embed " +
            "or reference executable code; this is typically used to embed or refer to JavaScript code.")


/*******************************************************************************************
 ********************************    Demarcating edits   ***********************************
 *******************************************************************************************/


val ELEMENT_DEL = HtmlElement("del", "Represents a range of text that has been " +
            "deleted from a document.")

val ELEMENT_INS = HtmlElement("ins", "Represents a range of text that has been " +
            "added to a document.")


/*******************************************************************************************
 **********************************    Table content   *************************************
 *******************************************************************************************/


val ELEMENT_CAPTION = HtmlElement("caption", "Specifies the caption (or title) of " +
            "a table, and if used is always the first child of a <table>.")

val ELEMENT_COL = HtmlElement("col", "Defines a column within a table and is used " +
            "for defining common semantics on all common cells.\n" +
            "It is generally found within a <colgroup> element.")

val ELEMENT_COLGROUP = HtmlElement("colgroup", "Defines a group of columns within a table.")

val ELEMENT_TABLE = HtmlElement("table", "Represents tabular data — that is, " +
            "information presented in a two-dimensional table comprised of rows and columns of " +
            "cells containing data.")

val ELEMENT_TBODY = HtmlElement("tbody", "Encapsulates a set of table row " +
            "(<tr> elements, indicating that they comprise the body of the table (<table>).")

val ELEMENT_TD = HtmlElement("td", "Defines a cell of a table that contains data.\n" +
            "It participates in the table model.")

val ELEMENT_TFOOT = HtmlElement("tfoot", "Defines a set of rows summarizing the " +
            "columns of the table.")

val ELEMENT_TH = HtmlElement("th", "Defines a cell as header of a group of " +
            "table cells.\n" +
            "The exact nature of this group is defined by the scope and headers attributes.")

val ELEMENT_THEAD = HtmlElement("thead", "Defines a set of rows defining the head " +
            "of the columns of the table.")

val ELEMENT_TR = HtmlElement("tr", "Defines a row of cells in a table.\n" +
            "The row's cells can then be established using a mix of <td> (data cell) and <th> " +
            "(header cell) elements.\n" +
            "The HTML <tr> element specifies that the markup contained inside the <tr> block " +
            "comprises one row of a table, inside which the <th> and <td> elements create header and " +
            "data cells, respectively, within the row.")


/*******************************************************************************************
 ***************************************    Forms   ****************************************
 *******************************************************************************************/


val ELEMENT_BUTTON = HtmlElement("button", "Represents a clickable button, which " +
            "can be used in forms, or anywhere in a document that needs simple, standard button " +
            "functionality.")

val ELEMENT_DATALIST = HtmlElement("datalist", "Contains a set of <option> elements " +
            "that represent the values available for other controls.")

val ELEMENT_FIELDSET = HtmlElement("fieldset", "Used to group several controls as " +
            "well as labels (<label>) within a web form.")

val ELEMENT_FORM = HtmlElement("form", "Represents a document section that contains " +
            "interactive controls for submitting information to a web server.")

val ELEMENT_INPUT = HtmlElement("input", "Used to create interactive controls for " +
            "web-based forms in order to accept data from the user.")

val ELEMENT_LABEL = HtmlElement("label", "Represents a caption for an item in a " +
            "user interface.")

val ELEMENT_LEGEND = HtmlElement("legend", "Represents a caption for the content " +
            "of its parent <fieldset>.")

val ELEMENT_METER = HtmlElement("meter", "Represents either a scalar value within " +
            "a known range or a fractional value.")

val ELEMENT_OPTGROUP = HtmlElement("optgroup", "Creates a grouping of options " +
            "within a <select> element.")

val ELEMENT_OPTION = HtmlElement("option", "Used to define an item contained in a " +
            "<select>, an <optgroup>, or a <datalist> element.\n" +
            "As such, <option> can represent menu items in popups and other lists of items in an " +
            "HTML document.")

val ELEMENT_OUTPUT = HtmlElement("output", "A container element into which a site " +
            "or app can inject the results of a calculation or the outcome of a user action.")

val ELEMENT_PROGRESS = HtmlElement("progress", "Displays an indicator showing the " +
            "completion progress of a task, typically displayed as a progress bar.")

val ELEMENT_SELECT = HtmlElement("select", "Represents a control that provides a " +
            "menu of options:")

val ELEMENT_TEXTAREA = HtmlElement("textarea", "Represents a multi-line plain-text " +
            "editing control, useful when you want to allow users to enter a sizeable amount of " +
            "free-form text, for example a comment on a review or feedback form.")


/*******************************************************************************************
 ********************************    Interactive elements   ********************************
 *******************************************************************************************/


val ELEMENT_DETAILS = HtmlElement("details", "Creates a disclosure widget in which " +
            "information is visible only when the widget is toggled into an \"open\" state.")

val ELEMENT_DIALOG = HtmlElement("dialog", "Represents a dialog box or other " +
            "interactive component, such as an inspector or window.")

val ELEMENT_MENU = HtmlElement("menu", "Represents a group of commands that a user " +
            "can perform or activate.\n" +
            "This includes both list menus, which might appear across the top of a screen, as well as " +
            "context menus, such as those that might appear underneath a button after it has been clicked.")

val ELEMENT_MENUITEM = HtmlElement("menuitem", "Represents a command that a user " +
            "is able to invoke through a popup menu.\n" +
            "This includes context menus, as well as menus that might be attached to a menu button.", true)

val ELEMENT_SUMMARY = HtmlElement("summary", "Specifies a summary, caption, or " +
            "legend for a <details> element's disclosure box.")


/*******************************************************************************************
 ***********************************    Web Components   ***********************************
 *******************************************************************************************/


val ELEMENT_CONTENT = HtmlElement("content", "An obsolete part of the Web " +
            "Components suite of technologies.\n" +
            "It was used inside of Shadow DOM as an insertion point, and wasn't meant to be used in " +
            "ordinary HTML.", true)

val ELEMENT_ELEMENT = HtmlElement("element", "The obsolete <element> element was " +
            "part of the Web Components specification; it was intended to be used to define new " +
            "custom DOM elements.", true)

val ELEMENT_SHADOW = HtmlElement("shadow", "An obsolete part of the Web Components " +
            "technology suite.\n" +
            "It was intended to be used as a shadow DOM insertion point.", true)

val ELEMENT_SLOT = HtmlElement("slot", "Part of the Web Components technology suite.\n" +
            "It is a placeholder inside a web component that you can fill with your own markup, which " +
            "lets you create separate DOM trees and present them together.")

val ELEMENT_TEMPLATE = HtmlElement("template", "A mechanism for holding client-side " +
            "content that is not to be rendered when a page is loaded but may subsequently be " +
            "instantiated during runtime using JavaScript.")


/*******************************************************************************************
 *************************************    Attributes   *************************************
 *******************************************************************************************/

val ATTRIBUTE_ACCEPT = HtmlAttribute("accept", "List of types the server accepts, " +
            "typically a file type.", elements = listOf(ELEMENT_FORM, ELEMENT_INPUT))

val ATTRIBUTE_ACCEPT_CHARSET = HtmlAttribute("accept-charset", "List of supported " +
        "charsets.", elements = listOf(ELEMENT_FORM))

val ATTRIBUTE_ACCESSKEY = HtmlAttribute("accesskey", "Defines a keyboard shortcut " +
            "to activate or add focus to the element.")

val ATTRIBUTE_ACTION = HtmlAttribute("action", "The URI of a program that processes the " +
            "information submitted via the form.", elements = listOf(ELEMENT_FORM))

val ATTRIBUTE_ALLIGN = HtmlAttribute("allign", "Specifies the horizontal alignment " +
            "of the element.", elements = listOf(ELEMENT_APPLET, ELEMENT_CAPTION, ELEMENT_COL,
        ELEMENT_COLGROUP, ElEMENT_HR, ELEMENT_IFRAME, ELEMENT_IMG, ELEMENT_TABLE, ELEMENT_TBODY,
        ELEMENT_TD, ELEMENT_TFOOT, ELEMENT_TH, ELEMENT_THEAD, ELEMENT_TR))

val ATTRIBUTE_ALT = HtmlAttribute("alt", "Alternative text in case an image can't " +
        "be displayed.", elements = listOf(ELEMENT_APPLET, ELEMENT_AREA, ELEMENT_IMG, ELEMENT_INPUT))

val ATTRIBUTE_ASYNC = HtmlAttribute("async", "Alternative text in case an image " +
        "can't be displayed.", elements = listOf(ELEMENT_SCRIPT))

val ATTRIBUTE_AUTOCAPITALIZE = HtmlAttribute("autocapitalize", "Controls whether " +
            "and how text input is automatically capitalized as it is entered/edited by the user.")

val ATTRIBUTE_AUTOCOMPLETE = HtmlAttribute("autocomplete", "Indicates whether " +
            "controls in this form can by default have their values automatically completed by the " +
        "browser.", elements = listOf(ELEMENT_FORM, ELEMENT_INPUT, ELEMENT_TEXTAREA))

val ATTRIBUTE_AUTOFOCUS = HtmlAttribute("autofocus", "The element should be " +
            "automatically focused after the page loaded.", elements = listOf(ELEMENT_BUTTON,
        ELEMENT_INPUT, ELEMENT_SELECT, ELEMENT_TEXTAREA))

val ATTRIBUTE_AUTOPLAY = HtmlAttribute("autoplay", "The audio or video should " +
            "play as soon as possible.", elements = listOf(ELEMENT_AUDIO, ELEMENT_VIDEO))

val ATTRIBUTE_BGCOLOR = HtmlAttribute("bgcolor", "Background color of the element.\n" +
            "Note: This is a legacy attribute.\n" +
            "Please use the CSS background-color property instead.", elements = listOf(ELEMENT_BODY,
        ELEMENT_COL, ELEMENT_COLGROUP, ELEMENT_TABLE, ELEMENT_TBODY, ELEMENT_TFOOT, ELEMENT_TD,
        ELEMENT_TH, ELEMENT_TR))

val ATTRIBUTE_BORDER = HtmlAttribute("border", "The border width.\n" +
            "Note: This is a legacy attribute.\n" +
            "Please use the CSS border property instead.", elements = listOf(ELEMENT_IMG,
        ELEMENT_OBJECT, ELEMENT_TABLE))

val ATTRIBUTE_BUFFERED = HtmlAttribute("buffered", "Contains the time range of " +
            "already buffered media.", elements = listOf(ELEMENT_AUDIO, ELEMENT_VIDEO))

val ATTRIBUTE_CHARSET = HtmlAttribute("charset", "Declares the character encoding " +
            "of the page or script.", elements = listOf(ELEMENT_META, ELEMENT_SCRIPT))

val ATTRIBUTE_CHECKED = HtmlAttribute("checked", "Indicates whether the element " +
            "should be checked on page load.", elements = listOf(ELEMENT_INPUT))

val ATTRIBUTE_CITE = HtmlAttribute("cite", "Contains a URI which points to the " +
            "source of the quote or change.", elements = listOf(ELEMENT_BLOCKQUOTE, ELEMENT_DEL,
        ELEMENT_INS, ELEMENT_Q))

val ATTRIBUTE_CLASS = HtmlAttribute("class", "Often used with CSS to style elements with " +
            "common properties.")

val ATTRIBUTE_CODE = HtmlAttribute("code", "Specifies the URL of the applet's class file " +
            "to be loaded and executed.", elements = listOf(ELEMENT_APPLET))

val ATTRIBUTE_CODEBASE = HtmlAttribute("codebase", "This attribute gives the " +
            "absolute or relative URL of the directory where applets' .class files referenced by the " +
            "code attribute are stored.", elements = listOf(ELEMENT_APPLET))

val ATTRIBUTE_COLOR = HtmlAttribute("color", "This attribute sets the text color " +
            "using either a named color or a color specified in the hexadecimal #RRGGBB format.\n" +
            "Note: This is a legacy attribute.\n" +
            "Please use the CSS color property instead.", true, listOf(ElEMENT_HR))

val ATTRIBUTE_COLS = HtmlAttribute("cols", "Defines the number of columns in a " +
        "textarea.", elements = listOf(ELEMENT_TEXTAREA))

val ATTRIBUTE_COLSPAN = HtmlAttribute("colspan", "The colspan attribute defines " +
            "the number of columns a cell should span.", elements = listOf(ELEMENT_TD, ELEMENT_TH))

val ATTRIBUTE_CONTENT = HtmlAttribute("content", "A value associated with http-equiv or " +
            "name depending on the context.", elements = listOf(ELEMENT_META))

val ATTRIBUTE_CONTENTEDITABLE = HtmlAttribute("contenteditable", "Indicates whether " +
            "the element's content is editable.")

val ATTRIBUTE_CONTEXTMENU = HtmlAttribute("contextmenu", "Defines the ID of a " +
            "<menu> element which will serve as the element's context menu.")

val ATTRIBUTE_CONTROLS = HtmlAttribute("controls", "Indicates whether the browser " +
            "should show playback controls to the user.", elements = listOf(ELEMENT_AUDIO, ELEMENT_VIDEO))

val ATTRIBUTE_COORDS = HtmlAttribute("coords", "A set of values specifying the " +
            "coordinates of the hot-spot region.", elements = listOf(ELEMENT_AREA))

val ATTRIBUTE_CROSSORIGIN = HtmlAttribute("crossorigin", "How the element handles " +
            "cross-origin requests", elements = listOf(ELEMENT_AUDIO, ELEMENT_IMG, ELEMENT_LINK,
        ELEMENT_SCRIPT, ELEMENT_VIDEO))

val ATTRIBUTE_DATA = HtmlAttribute("data", "Specifies the URL of the resource.",
        elements = listOf(ELEMENT_OBJECT))

val ATTRIBUTE_DATETIME = HtmlAttribute("datetime", "Indicates the date and time " +
        "associated with the element.", elements = listOf(ELEMENT_DEL, ELEMENT_INS, ELEMENT_TIME))

val ATTRIBUTE_DEFER = HtmlAttribute("defer", "Indicates that the script should be " +
        "executed after the page has been parsed.", elements = listOf(ELEMENT_SCRIPT))

val ATTRIBUTE_DIR = HtmlAttribute("dir", "Defines the text direction.\n" +
        "Allowed values are ltr (Left-To-Right) or rtl (Right-To-Left)")

val ATTRIBUTE_DIRNAME = HtmlAttribute("dirname", null, elements = listOf(ELEMENT_INPUT,
        ELEMENT_TEXTAREA))

val ATTRIBUTE_DISABLED = HtmlAttribute("disabled", "Indicates whether the user " +
        "can interact with the element.", elements = listOf(ELEMENT_BUTTON, ELEMENT_FIELDSET,
        ELEMENT_INPUT, ELEMENT_OPTGROUP, ELEMENT_OPTION, ELEMENT_SELECT, ELEMENT_TEXTAREA))

val ATTRIBUTE_DOWNLOAD = HtmlAttribute("download", "Indicates that the hyperlink " +
        "is to be used for downloading a resource.", elements = listOf(ELEMENT_A, ELEMENT_AREA))

val ATTRIBUTE_DRAGGABLE = HtmlAttribute("draggable", "Defines whether the element " +
        "can be dragged.")

val ATTRIBUTE_DROPZONE = HtmlAttribute("dropzone", "Indicates that the element " +
        "accept the dropping of content on it.")

val ATTRIBUTE_ENCTYPE = HtmlAttribute("enctype", "Defines the content type of the " +
        "form date when the method is POST.", elements = listOf(ELEMENT_FORM))

val ATTRIBUTE_FOR = HtmlAttribute("for", "Describes elements which belongs to this " +
        "one.", elements = listOf(ELEMENT_LABEL, ELEMENT_OUTPUT))

val ATTRIBUTE_FORM = HtmlAttribute("form", "Indicates the form that is the owner " +
        "of the element.", elements = listOf(ELEMENT_BUTTON, ELEMENT_FIELDSET, ELEMENT_INPUT,
        ELEMENT_LABEL, ELEMENT_METER, ELEMENT_OBJECT, ELEMENT_OUTPUT, ELEMENT_PROGRESS,
        ELEMENT_SELECT, ELEMENT_TEXTAREA))

val ATTRIBUTE_FORMACTION = HtmlAttribute("formaction", "Indicates the action of " +
        "the element, overriding the action defined in the <form>.", elements = listOf(ELEMENT_INPUT,
        ELEMENT_BUTTON))

val ATTRIBUTE_HEADERS = HtmlAttribute("headers", "IDs of the <th> elements which " +
        "applies to this element.", elements = listOf(ELEMENT_TD, ELEMENT_TH))

val ATTRIBUTE_HEIGHT = HtmlAttribute("height", "Specifies the height of the element.\n" +
        "Note: In some instances, such as <div>, this is a legacy attribute, in which case the " +
        "CSS height property should be used instead.", elements = listOf(ELEMENT_CANVAS,
        ELEMENT_EMBED, ELEMENT_IFRAME, ELEMENT_IMG, ELEMENT_INPUT, ELEMENT_OBJECT, ELEMENT_VIDEO))

val ATTRIBUTE_HIDDEN = HtmlAttribute("hidden", "Prevents rendering of given " +
        "element, while keeping child elements, e.g. script elements, active.")

val ATTRIBUTE_HIGH = HtmlAttribute("high", "Indicates the lower bound of the upper " +
        "range.", elements = listOf(ELEMENT_METER))

val ATTRIBUTE_HREF = HtmlAttribute("href", "The URL of a linked resource.",
        elements = listOf(ELEMENT_A, ELEMENT_AREA, ELEMENT_LINK))

val ATTRIBUTE_HREFLANG = HtmlAttribute("hreflang", "Specifies the language of the " +
        "linked resource", elements = listOf(ELEMENT_A, ELEMENT_AREA, ELEMENT_LINK))

val ATTRIBUTE_HTTP_EQUIV = HtmlAttribute("http-equiv", null, elements = listOf(ELEMENT_META))

val ATTRIBUTE_ID = HtmlAttribute("id", "Often used with CSS to style a specific element.\n" +
        "The value of this attribute must be unique.")

val ATTRIBUTE_INTEGRITY = HtmlAttribute("integrity", "Security Feature that allows " +
        "browsers to verify what they fetch.", elements = listOf(ELEMENT_LINK, ELEMENT_SCRIPT))

val ATTRIBUTE_ISMAP = HtmlAttribute("ismap", "Indicates that the image is part of a " +
        "server-side image map.", elements = listOf(ELEMENT_IMG))

val ATTRIBUTE_ITEMPROP = HtmlAttribute("itemprop", null)

val ATTRIBUTE_LANG = HtmlAttribute("lang", "Defines the language used in the element.")

val ATTRIBUTE_LANGUAGE = HtmlAttribute("language", "Defines the script language " +
        "used in the element.", elements = listOf(ELEMENT_SCRIPT))

val ATTRIBUTE_LIST = HtmlAttribute("list", "Identifies a list of pre-defined " +
        "options to suggest to the user.", elements = listOf(ELEMENT_INPUT))

val ATTRIBUTE_LOOP = HtmlAttribute("loop", "Indicates whether the media should " +
        "start playing from the start when it's finished.", elements = listOf(ELEMENT_AUDIO,
        ELEMENT_VIDEO))

val ATTRIBUTE_LOW = HtmlAttribute("low", "Indicates the upper bound of the lower " +
        "range.", elements = listOf(ELEMENT_METER))

val ATTRIBUTE_MANIFEST = HtmlAttribute("manifest", "Specifies the URL of the " +
        "document's cache manifest.", elements = listOf(ELEMENT_HTML))

val ATTRIBUTE_MAX = HtmlAttribute("max", "Indicates the maximum value allowed.",
        elements = listOf(ELEMENT_INPUT, ELEMENT_METER, ELEMENT_PROGRESS))

val ATTRIBUTE_MAXLENGTH = HtmlAttribute("maxlength", "Defines the maximum number " +
        "of characters allowed in the element.", elements = listOf(ELEMENT_INPUT, ELEMENT_TEXTAREA))

val ATTRIBUTE_MINLENGTH = HtmlAttribute("minlength", "Defines the minimum number of " +
        "characters allowed in the element.", elements = listOf(ELEMENT_INPUT, ELEMENT_TEXTAREA))

val ATTRIBUTE_MEDIA = HtmlAttribute("media", "Specifies a hint of the media for " +
        "which the linked resource was designed.", elements = listOf(ELEMENT_A, ELEMENT_AREA,
        ELEMENT_LINK, ELEMENT_SOURCE, ELEMENT_STYLE))

val ATTRIBUTE_METHOD = HtmlAttribute("method", "Defines which HTTP method to use " +
        "when submitting the form.\n" +
        "Can be GET (default) or POST.", elements = listOf(ELEMENT_FORM))

val ATTRIBUTE_MIN = HtmlAttribute("min", "Indicates the minimum value allowed.",
        elements = listOf(ELEMENT_INPUT, ELEMENT_METER))

val ATTRIBUTE_MULTIPLE = HtmlAttribute("multiple", "Indicates whether multiple " +
        "values can be entered in an input of the type email or file.", elements = listOf(ELEMENT_INPUT,
        ELEMENT_SELECT))

val ATTRIBUTE_MUTED = HtmlAttribute("muted", "Indicates whether the audio will be " +
        "initially silenced on page load.", elements = listOf(ELEMENT_AUDIO, ELEMENT_VIDEO))

val ATTRIBUTE_NAME = HtmlAttribute("name", "Name of the element.\n" +
        "For example used by the server to identify the fields in form submits.",
        elements = listOf(ELEMENT_BUTTON, ELEMENT_FORM, ELEMENT_FIELDSET, ELEMENT_IFRAME,
                ELEMENT_INPUT, ELEMENT_OBJECT, ELEMENT_OUTPUT, ELEMENT_SELECT, ELEMENT_TEXTAREA,
                ELEMENT_MAP, ELEMENT_META, ELEMENT_PARAM))

val ATTRIBUTE_NOVALIDATE = HtmlAttribute("novalidate", "This attribute indicates " +
        "that the form shouldn't be validated when submitted.", elements = listOf(ELEMENT_FORM))

val ATTRIBUTE_OPEN = HtmlAttribute("open", "Indicates whether the details will be " +
        "shown on page load.", elements = listOf(ELEMENT_DETAILS))

val ATTRIBUTE_OPTIMUM = HtmlAttribute("optimum", "Indicates the optimal numeric " +
        "value.", elements = listOf(ELEMENT_METER))

val ATTRIBUTE_PATTERN = HtmlAttribute("pattern", "Defines a regular expression " +
        "which the element's value will be validated against.", elements = listOf(ELEMENT_INPUT))

val ATTRIBUTE_PING = HtmlAttribute("ping", null, elements = listOf(ELEMENT_A, ELEMENT_AREA))

val ATTRIBUTE_PLACEHOLDER = HtmlAttribute("placeholder", "Provides a hint to the " +
        "user of what can be entered in the field.", elements = listOf(ELEMENT_INPUT, ELEMENT_TEXTAREA))

val ATTRIBUTE_POSTER = HtmlAttribute("poster", "A URL indicating a poster frame to " +
        "show until the user plays or seeks.", elements = listOf(ELEMENT_VIDEO))

val ATTRIBUTE_PRELOAD = HtmlAttribute("preload", "Indicates whether the whole " +
        "resource, parts of it or nothing should be preloaded.", elements = listOf(ELEMENT_AUDIO,
        ELEMENT_VIDEO))

val ATTRIBUTE_READONLY = HtmlAttribute("readonly", "Indicates whether the element " +
        "can be edited.", elements = listOf(ELEMENT_INPUT, ELEMENT_TEXTAREA))

val ATTRIBUTE_REL = HtmlAttribute("rel", "Specifies the relationship of the target " +
        "object to the link object.", elements = listOf(ELEMENT_A, ELEMENT_AREA, ELEMENT_LINK))

val ATTRIBUTE_REQUIRED = HtmlAttribute("required", "Indicates whether this element " +
        "is required to fill out or not.", elements = listOf(ELEMENT_INPUT, ELEMENT_SELECT, ELEMENT_TEXTAREA))

val ATTRIBUTE_REVERSED = HtmlAttribute("reversed", "Indicates whether the list " +
        "should be displayed in a descending order instead of a ascending.", elements = listOf(ELEMENT_OL))

val ATTRIBUTE_ROWS = HtmlAttribute("rows", "Defines the number of rows in a text " +
        "area.", elements = listOf(ELEMENT_TEXTAREA))

val ATTRIBUTE_ROWSPAN = HtmlAttribute("rowspan", "Defines the number of rows a " +
        "table cell should span over.", elements = listOf(ELEMENT_TD, ELEMENT_TH))

val ATTRIBUTE_SANDBOX = HtmlAttribute("sandbox", "Stops a document loaded in an " +
        "iframe from using certain features (such as submitting forms or opening new windows).",
        elements = listOf(ELEMENT_IFRAME))

val ATTRIBUTE_SCOPE = HtmlAttribute("scope", "Defines the cells that the header " +
        "test (defined in the th element) relates to.", elements = listOf(ELEMENT_TH))

val ATTRIBUTE_SCOPED = HtmlAttribute("scoped", null, elements = listOf(ELEMENT_STYLE))

val ATTRIBUTE_SELECTED = HtmlAttribute("selected", "Defines a value which will be " +
        "selected on page load.", elements = listOf(ELEMENT_OPTION))

val ATTRIBUTE_SHAPE = HtmlAttribute("shape", null, elements = listOf(ELEMENT_A,
        ELEMENT_AREA))

val ATTRIBUTE_SIZE = HtmlAttribute("size", "Defines the width of the element " +
        "(in pixels).\n" +
        "If the element's type attribute is text or password then it's the number of characters.",
        elements = listOf(ELEMENT_INPUT, ELEMENT_SELECT))

val ATTRIBUTE_SIZES = HtmlAttribute("sizes", null, elements = listOf(ELEMENT_LINK,
        ELEMENT_IMG, ELEMENT_SOURCE))

val ATTRIBUTE_SLOT = HtmlAttribute("slot", "Assigns a slot in a shadow DOM shadow " +
        "tree to an element.")

val ATTRIBUTE_SPAN = HtmlAttribute("span", null, elements = listOf(ELEMENT_COL,
        ELEMENT_COLGROUP))

val ATTRIBUTE_SPELLCHECK = HtmlAttribute("spellcheck", "Indicates whether spell " +
        "checking is allowed for the element.")

val ATTRIBUTE_SRC = HtmlAttribute("src", "The URL of the embeddable content.",
        elements = listOf(ELEMENT_AUDIO, ELEMENT_EMBED, ELEMENT_IFRAME, ELEMENT_IMG, ELEMENT_INPUT,
                ELEMENT_SCRIPT, ELEMENT_SOURCE, ELEMENT_VIDEO))

val ATTRIBUTE_SRCDOC = HtmlAttribute("srcdoc", null, elements = listOf(ELEMENT_IFRAME))

val ATTRIBUTE_SRCSET = HtmlAttribute("srcset", null, elements = listOf(ELEMENT_IMG))

val ATTRIBUTE_START = HtmlAttribute("start", "Defines the first number if other " +
        "than 1.", elements = listOf(ELEMENT_OL))

val ATTRIBUTE_STEP = HtmlAttribute("step", null, elements = listOf(ELEMENT_INPUT))

val ATTRIBUTE_STYLE = HtmlAttribute("style", "Defines CSS styles which will " +
        "override styles previously set.")

val ATTRIBUTE_SUMMARY = HtmlAttribute("summary", null, elements = listOf(ELEMENT_TABLE))

val ATTRIBUTE_TABINDEX = HtmlAttribute("tabindex", "Overrides the browser's default " +
        "tab order and follows the one specified instead.")

val ATTRIBUTE_TARGET = HtmlAttribute("target", null, elements = listOf(ELEMENT_A,
        ELEMENT_AREA, ELEMENT_FORM))

val ATTRIBUTE_TITLE = HtmlAttribute("title", "Text to be displayed in a tooltip " +
        "when hovering over the element.")

val ATTRIBUTE_TRANSLATE = HtmlAttribute("translate", "Specify whether an element’s " +
        "attribute values and the values of its Text node children are to be translated when the " +
        "page is localized, or whether to leave them unchanged.")

val ATTRIBUTE_TYPE = HtmlAttribute("type", "Defines the type of the element.",
        elements = listOf(ELEMENT_BUTTON, ELEMENT_INPUT, ELEMENT_EMBED, ELEMENT_OBJECT,
                ELEMENT_SCRIPT, ELEMENT_SOURCE, ELEMENT_STYLE, ELEMENT_MENU))

val ATTRIBUTE_USEMAP = HtmlAttribute("usemap", null, elements = listOf(ELEMENT_IMG,
        ELEMENT_INPUT, ELEMENT_OBJECT))

val ATTRIBUTE_VALUE = HtmlAttribute("value", "Defines a default value which will " +
        "be displayed in the element on page load.", elements = listOf(ELEMENT_BUTTON, ELEMENT_OPTION,
        ELEMENT_INPUT, ElEMENT_LI, ELEMENT_METER, ELEMENT_PROGRESS, ELEMENT_PARAM))

val ATTRIBUTE_WIDTH = HtmlAttribute("width", "Establishes the element's width.",
        elements = listOf(ELEMENT_CANVAS, ELEMENT_EMBED, ELEMENT_IFRAME, ELEMENT_IMG, ELEMENT_INPUT,
                ELEMENT_OBJECT, ELEMENT_VIDEO))

val ATTRIBUTE_WRAP = HtmlAttribute("wrap", "Indicates whether the text should be " +
        "wrapped.", elements = listOf(ELEMENT_TEXTAREA))
