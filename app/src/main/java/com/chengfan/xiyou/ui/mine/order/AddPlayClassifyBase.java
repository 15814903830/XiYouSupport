package com.chengfan.xiyou.ui.mine.order;

import java.util.List;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class AddPlayClassifyBase {

    private List<?> news;
    private List<SubjectBean> subject;
    private List<?> accompanyPlay;

    public List<?> getNews() {
        return news;
    }

    public void setNews(List<?> news) {
        this.news = news;
    }

    public List<SubjectBean> getSubject() {
        return subject;
    }

    public void setSubject(List<SubjectBean> subject) {
        this.subject = subject;
    }

    public List<?> getAccompanyPlay() {
        return accompanyPlay;
    }

    public void setAccompanyPlay(List<?> accompanyPlay) {
        this.accompanyPlay = accompanyPlay;
    }

    public static class SubjectBean {
        /**
         * id : 1
         * images : Img/1.png
         * title : 守望先锋
         * areaTitles : null
         * gradeTitles : null
         * accompanyPlay : []
         */

        private int id;
        private String images;
        private String title;
        private String areaTitles;
        private String gradeTitles;
        private List<?> accompanyPlay;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAreaTitles() {
            return areaTitles;
        }

        public void setAreaTitles(String areaTitles) {
            this.areaTitles = areaTitles;
        }

        public String getGradeTitles() {
            return gradeTitles;
        }

        public void setGradeTitles(String gradeTitles) {
            this.gradeTitles = gradeTitles;
        }

        public List<?> getAccompanyPlay() {
            return accompanyPlay;
        }

        public void setAccompanyPlay(List<?> accompanyPlay) {
            this.accompanyPlay = accompanyPlay;
        }
    }
}
