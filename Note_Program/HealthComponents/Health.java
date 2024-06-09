package HealthComponents;

import java.io.Serializable;

public class Health implements Serializable{
    private String title;
    private String content;

    public Health(String _title, String _content){
        setTitle(_title);
        setContent(_content);
    }

    public void setTitle(String _title) { title = _title; }
    public void setContent(String _content) { content = _content; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
}
