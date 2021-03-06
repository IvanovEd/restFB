package com.spilna.sprava.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spilna.sprava.businesslogic.enums.Interest;
import com.spilna.sprava.businesslogic.utils.Utils;

import static com.spilna.sprava.businesslogic.enums.Oblast.*;

import com.spilna.sprava.businesslogic.enums.Oblast;
import com.spilna.sprava.model.InterestOfPost;
import com.spilna.sprava.model.Post;
import com.spilna.sprava.businesslogic.object.PostRO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.restfb.types.User;
import com.spilna.sprava.service.PostService;
import com.spilna.sprava.service.UserService;

/**
 * @author Ivanov
 * @version 1.0
 */

@Controller
@PropertySource("classpath:app.properties")
public class FacebookController {

    /**
     * Link to service users and post.
     */
    @Resource
    private Environment env;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private Utils utils;
    private String accessToken;
    private static String SCOPE;
    private static String REDIRECT_URI;
    private static String APP_ID;
    private static String APP_SECRET;
    private static String DIALOG_OAUTH;
    private static String ACCESS_TOKEN;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView homePage() {
        return new ModelAndView("index");// name of the jsp-file in the 'pages'
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView indexPage() {
        return new ModelAndView("index");
    }

    /**
     * The method performs a redirect to the login page on Facebook.
     *
     * @param request  the resulting value of HttpServletRequest
     * @param response corresponding to the value of HttpServletResponse
     * @throws IOException
     */
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public void getSignin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        init();
        response.setContentType("text/html");
        response.sendRedirect(DIALOG_OAUTH + "?client_id=" + APP_ID
                + "&redirect_uri=" + REDIRECT_URI + "&scope=" + SCOPE);
    }

    /**
     * The method that gets Access Token.
     *
     * @param request  the resulting value of HttpServletRequest
     * @param response corresponding to the value of HttpServletResponse
     * @throws IOException
     */
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public void accessCode(HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        String code = request.getParameter("code");
        if (code == null || code.equals("")) {
            throw new Exception("code from FB is null");
        }
        String urlAccessToken = ACCESS_TOKEN + "?client_id=" + APP_ID
                + "&redirect_uri=" + REDIRECT_URI + "&client_secret="
                + APP_SECRET + "&code=" + code;
        response.setContentType("text/html");
        this.setAccessToken(utils.getAccessToken(urlAccessToken));
        userService.addUser(this.getAccesToken());
        response.sendRedirect("http://localhost:8080/post");
    }

    /**
     * The method returns a JSP page "post" when the link "/post".
     * The user can post it status or see  of their posts.
     *
     * @param post
     * @return
     */

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public ModelAndView getMessageForm(@ModelAttribute("post") Post post) {

        ModelAndView modelAndView = new ModelAndView("post");
        List<com.restfb.types.Post> postList = utils.getListOfPostsFromFB(getAccesToken(), APP_SECRET);
        User me = utils.getUserFromFB(getAccesToken(), APP_SECRET);
		/*
		 * variable contains user Id
		 */
        String idU = me.getId();
        postService.saveOrUpdatePost(postList,idU);

        List<PostRO> postlist = postService.getMessage(idU);

        modelAndView.addObject("post", postlist);
        modelAndView.addObject("user", userService.getUser(idU));
        return modelAndView;
    }

    /**
     * Definition of user input. Saving the new post to the database
     * and publishing the post on facebook
     *
     * @param post
     * @return ModelAndView redirect:/social/facebook/post.html
     * @throws ClientProtocolException
     * @throws IOException
     */
    @RequestMapping(value = "/savePost", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView saveUserMessage(@ModelAttribute("post") String post, @RequestParam String userId)
            throws IOException {

        //postService.addMessage(this.getAccesToken(), post, userId);

        System.out.println("Save User Message");
        return new ModelAndView("redirect:/post.html");// redirected

    }

    @RequestMapping(value = "/ukraineMap", method = RequestMethod.GET)
    public ModelAndView lookMap(@RequestParam(required = false)  Long interest, String page, @RequestParam(required = false) String obl)
            throws IOException {
        ModelAndView modelAndView = new ModelAndView("ukrainMap");
        List<Post> postList = postService.getAllPostInf();
        Map<String, String> map = new HashMap<>();
        if (obl != null) {
            List<String> users = new ArrayList<>();
            for (Post post : postList) {
                if (interest.toString().equals(post.getInterestOfPost().getInterest())) {

                    if (CollectionUtils.isEmpty(users)) {
                        users.add(post.getIdUser());
                    } else if (!users.contains(post.getIdUser())) {
                        users.add(post.getIdUser());
                    }
                }
            }
            map.put(VINNITSA.getValue(), String.valueOf(users.size()));
            map.put(KUIVSKA.getValue(), "0");
            map.put(ODESSA.getValue(), "0");
            map.put(LVIVSKA.getValue(), "0");
            map.put(IVANOFRANKIVSK.getValue(), "0");
            map.put(ZHITOMERSKA.getValue(), "0");
            map.put(HARKIVSKA.getValue(), "0");
            map.put(SUMSKA.getValue(), "0");
            map.put(DONETSKA.getValue(), "0");
            map.put(LUGANSKA.getValue(), "0");
            map.put(MIKOLAEVSKA.getValue(), "0");
            map.put(VOLINSKA.getValue(), "0");
            map.put(CHERNIGIVSKA.getValue(), "0");
            map.put(CHERKASKA.getValue(), "0");
            map.put(ZAKARPATSKA.getValue(), "0");
            map.put(ZAPORIZHSKA.getValue(), "0");
            map.put(KIROVOGRADSKA.getValue(), "0");
            map.put(TERNOPILSKA.getValue(), "0");
            map.put(HMELNITSKA.getValue(), "0");
            map.put(DNIPROPETROVSKA.getValue(), "0");
            map.put(POLTAVSKA.getValue(), "0");
            map.put(RIVNENSKA.getValue(), "0");
            map.put(HERSONSKA.getValue(), "0");
            map.put(CHERNIVETSKA.getValue(), "0");
            map.put(KRYM.getValue(), "0");
        } else {
            map.put(KUIVSKA.getValue(), "80");
            map.put(ODESSA.getValue(), "44");
            map.put(VINNITSA.getValue(), "55");
            map.put(LVIVSKA.getValue(), "89");
            map.put(IVANOFRANKIVSK.getValue(), "200");
            map.put(ZHITOMERSKA.getValue(), "67");
            map.put(HARKIVSKA.getValue(), "234");
            map.put(SUMSKA.getValue(), "156");
            map.put(DONETSKA.getValue(), "437");
            map.put(LUGANSKA.getValue(), "777");
            map.put(MIKOLAEVSKA.getValue(), "34");
            map.put(VOLINSKA.getValue(), "123");
            map.put(CHERNIGIVSKA.getValue(), "400");
            map.put(CHERKASKA.getValue(), "542");
            map.put(ZAKARPATSKA.getValue(), "160");
            map.put(ZAPORIZHSKA.getValue(), "77");
            map.put(KIROVOGRADSKA.getValue(), "99");
            map.put(TERNOPILSKA.getValue(), "130");
            map.put(HMELNITSKA.getValue(), "444");
            map.put(DNIPROPETROVSKA.getValue(), "340");
            map.put(POLTAVSKA.getValue(), "300");
            map.put(RIVNENSKA.getValue(), "342");
            map.put(HERSONSKA.getValue(), "345");
            map.put(CHERNIVETSKA.getValue(), "234");
            map.put(KRYM.getValue(), "111");
        }
        modelAndView.addObject("valuesMap", map);

        List<PostRO> postROList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(postList)) {

        }
        for (Post post : postList) {
            postROList.add(new PostRO(post));
        }
        modelAndView.addObject("valuesForPio", utils.getPercentOfInterestMap(postROList, false));
        modelAndView.addObject("page", page);
        StringBuilder stringBuilder = new StringBuilder();
        for (Oblast oblast : Oblast.values()) {
            stringBuilder.append(oblast.getValue() + ",");
        }
        System.out.println(stringBuilder.toString());
        return modelAndView;

    }

    @RequestMapping(value = "/selectInterest", method = RequestMethod.GET)
    public ModelAndView select(@RequestParam String id, @RequestParam String interest) throws IOException {
        Post post = postService.getPostByID(Long.valueOf(id));
        updateOrCreateNewInterestOfPost(post, interest);
        postService.updatePost(post);
        return new ModelAndView("redirect:/post.html");
    }

    public void setAccessToken(String token) {
        this.accessToken = token;
    }

    public String getAccesToken() {
        return this.accessToken;
    }

    private void updateOrCreateNewInterestOfPost(Post post, String interest) {
        if (post.getInterestOfPost() != null) {
            post.getInterestOfPost().setInterest(interest);
        } else {
            InterestOfPost interestOfPost = new InterestOfPost();
            interestOfPost.setInterest(interest);
            interestOfPost.setPost(post);
            post.setInterestOfPost(interestOfPost);
        }
    }

    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
    public ModelAndView adminPage() throws UnsupportedEncodingException {

        ModelAndView modelAndView = new ModelAndView();

        List<Post> postlist = postService.getAllPostInf();
        List<PostRO> postROs = new ArrayList<>();
        for (Post post : postlist) {
            String userName = userService.getUser(post.getIdUser()).get(0).getName();
            PostRO postRO = new PostRO(post);
            postRO.setUserName(userName);
            postROs.add(postRO);
        }

        modelAndView.addObject("post", postROs);
        modelAndView.setViewName("admin");
        return modelAndView;

    }

    private void init() {
        SCOPE = env.getRequiredProperty("fb.scope");
        REDIRECT_URI = env.getRequiredProperty("fb.redirect.url");
        APP_ID = env.getRequiredProperty("fb.app.id");
        APP_SECRET = env.getRequiredProperty("fb.app.secret");
        DIALOG_OAUTH = env.getRequiredProperty("fb.dialog.oauth");
        ACCESS_TOKEN = env.getRequiredProperty("fb.access.token.url");
    }
}
