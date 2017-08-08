package com.epam.javalab.hotelproject.utils.tag;

import com.epam.javalab.hotelproject.model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class HeaderTag extends TagSupport {
    private static final Logger LOGGER = Logger.getLogger(HeaderTag.class);
    private ResourceBundle resourceBundle;
    private HttpSession    session;

    @Override
    public int doStartTag() throws JspException {
        initialize();
        User user;
        try {
            printHeaderTop();
            if (session.getAttribute("user") != null) {
                user = (User) session.getAttribute("user");
                pageContext.getOut().write("<span>" + user.getName() + "</span>");
            } else {
                printLoginForm();
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

    @Override
    public int doAfterBody() throws JspException {

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().write("</div>");
            pageContext.getOut().write("</nav>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    private void initialize() {
        session = pageContext.getSession();
        Locale locale = new Locale(session.getAttribute("lang").toString());

        LOGGER.info("Got locale from session: " + locale.getLanguage());

        resourceBundle = ResourceBundle.getBundle("localization.locale", locale);
    }

    private void printHeaderTop() throws IOException {
        pageContext.getOut().write("<nav class=\"navbar navbar-inverse navbar-fixed-top\">");
        pageContext.getOut().write("<div class=\"container\">");
        pageContext.getOut().write("<div class=\"navbar-header\">");
        pageContext.getOut()
                .write("<button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\">");
        pageContext.getOut().write("<span class=\"sr-only\">Toggle navigation</span>");
        pageContext.getOut().write("<span class=\"icon-bar\"></span>");
        pageContext.getOut().write("<span class=\"icon-bar\"></span>");
        pageContext.getOut().write("<span class=\"icon-bar\"></span>");
        pageContext.getOut().write("</button>");
        pageContext.getOut().write("<table><tr><td><ul class=\"navbar-lang-switcher\">");
        pageContext.getOut().write("<select onchange=\"switchLang(this)\">");
        if (session.getAttribute("lang").equals("en")) {
            /*pageContext.getOut().write("<li onClick=\"switchLang(this)\" class=\"navbar-lang active-lang\"><span>EN</span></li>");
            pageContext.getOut().write("<li onClick=\"switchLang(this)\" class=\"navbar-lang\"><span>RU</span></li>");*/
            pageContext.getOut().write("<option value=\"en\" selected=\"selected\">EN</option>");
            pageContext.getOut().write("<option value=\"ru\">RU</option>");
        } else {
            /*pageContext.getOut().write("<li class=\"navbar-lang\"><span>EN</span></li>");
            pageContext.getOut().write("<li class=\"navbar-lang active-lang\"><span>RU</span></li>");*/
            pageContext.getOut().write("<option value=\"en\">EN</option>");
            pageContext.getOut().write("<option value=\"ru\" selected=\"selected\">RU</option>");
        }
        pageContext.getOut().write("</ul></td><td>");
        pageContext.getOut()
                .write("<a class=\"navbar-brand\" href=\"/\">" + resourceBundle.getString("project.name") + "</a>");
        pageContext.getOut().write("</td></tr></table>");
        pageContext.getOut().write("</div>");
    }

    private void printLoginForm() throws IOException {
        pageContext.getOut().write("<div id=\"navbar\" class=\"navbar-collapse collapse\">");
        pageContext.getOut().write("<form class=\"navbar-form navbar-right\" action=\"/login\" method=\"post\">");
        pageContext.getOut().write("<div class=\"form-group\">");
        pageContext.getOut().write("<input type=\"email\" name=\"login\" placeholder=\"Email\" class=\"form-control\">");
        pageContext.getOut().write("</div>");
        pageContext.getOut().write("<div class=\"form-group\">");
        pageContext.getOut().write("<input type=\"password\" name=\"password\" placeholder=\"Password\" class=\"form-control\">");
        pageContext.getOut().write("</div>");
        pageContext.getOut().write("<button type=\"submit\" class=\"btn btn-success\">" + resourceBundle.getString("button.sign_in") + "</button>");
        pageContext.getOut().write("<a class=\"btn btn-success btn-md\" href=\"/registration\" role=\"button\">" + resourceBundle.getString("button.sign_up") + "</a>");
        pageContext.getOut().write("</form>");
        pageContext.getOut().write("</div>");
    }
}
