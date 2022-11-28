package com.fedag.CSR.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "front_params")
public class FrontParams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "active_window")
    private boolean activeWindow;
    @Column(name = "background_case")
    private String backgroundCase;
    @Column(name = "background_main_bottom")
    private String backgroundMainBottom;
    @Column(name = "button_text")
    private String buttonText;
    @Column(name = "color_background")
    private String colorBackground;
    @Column(name = "color_background_one")
    private String colorBackgroundOne;
    @Column(name = "color_background_two")
    private String colorBackgroundTwo;
    @Column(name = "color_button")
    private String colorButton;
    @Column(name = "color_buttons")
    private String colorButtons;
    @Column(name = "color_footer_down")
    private String colorFooterDown;
    @Column(name = "color_footer_up")
    private String colorFooterUp;
    @Column(name = "color_header_left")
    private String colorHeaderLeft;
    @Column(name = "color_header_right")
    private String colorHeaderRight;
    @Column(name = "footer_logo")
    private String footerLogo;
    @Column(name = "header_logo")
    private String headerLogo;
    @Column(name = "text_image")
    private String textImage;
    @Column(name = "title_text")
    private String titleText;
    @Column(name = "window_text_two")
    private String windowTextTwo;
}
