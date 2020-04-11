## Detailed doc for ProgressButton library

Quick setup can be found [here](Readme.md)

Java samples: [here](app/src/main/java/com/github/razir/progressexample/java)

## Showing Progress

![basic progress button example](https://raw.githubusercontent.com/razir/ProgressButton/master/gif/progress_default.gif)

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    //this is mandatory to bind your button to activity or fragment lifecycle
    bindProgressButton(myButton)

    // (Optional) Enable fade In / Fade out animations 
    // All parameters are OPTIONAL
    myButton.attachTextChangeAnimator {
    
       fadeOutMills = 150 // current text fade out time in milliseconds. default 150
       fadeInMills = 150 // current text fade in time in milliseconds. default 150
    
       useCurrentTextColor = false // by default is true. handling text color based on the current color settings
       
       textColor = Color.WHITE // override button text color with single color
       textColorRes = R.color.white // override button text color with single color resource
       textColorList: ColorStateList // override button text color with stateful color
    }

    // Show progress with "Loading" text. The final progress size will be (radius + stroke) * 2
    myButton.showProgress {
    
        buttonText = "Loading" // String value to show next to progress
        buttonTextRes = R.string.loading // text resource to show next to progress
        
        // progress drawable gravity relative to button text
        // possible values GRAVITY_TEXT_START, GRAVITY_TEXT_END and GRAVITY_CENTER
        gravity = DrawableButton.GRAVITY_TEXT_END  // default value is GRAVITY_TEXT_END
       
        textMarginRes = R.dimen.progressMargin //margin between text and progress. default 10dp
        textMarginPx = 30 //margin between text and progress in pixels. default 10dp
       
        progressColor = Color.WHITE  // progress color int
        progressColorRes = R.color.white  // progress color resource
        progressColors = intArrayOf(Color.WHITE, Color.BLACK)  // progress colors list
        
        
        progressRadiusRes = R.dimen.smallRadius // progress radius dimension resource. default 7.5dp
        progressRadiusPx = 50  // progress radius in pixels default 7.5dp
        
        progressStrokeRes = R.dimen.stroke3 // progress stroke dimension resource. default 2.5dp
        progressStrokePx = 50  // progress stroke in pixels. default 2.5dp
    }

    // Hide progress and show "Submit" text instead
    myButton.hideProgress(R.string.submit)
}
```

## Showing AnimatedDrawable


![animated drawable button example](https://raw.githubusercontent.com/razir/ProgressButton/master/gif/animated_drawable.gif)

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    //this is mandatory to bind your button to activity or fragment lifecycle
    bindProgressButton(myButton)

    // (Optional) Enable fade In / Fade out animations 
    // All parameters are OPTIONAL
    myButton.attachTextChangeAnimator {
        // same as Showing Progress above
    }
    
    // setup bounds is required to use AnimatedDrawable with library
    val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
    animatedDrawable.setBounds(0, 0, 50, 50)

    // Show progress with "Loading" text. The final progress size will be (radius + stroke) * 2
    myButton.showDrawable(animatedDrawable) {
    
        buttonText = "Done" // String value to show next to animated drawable
        buttonTextRes = R.string.done // text resource to show next to animated drawable
        
        // progress drawable gravity relative to button text
        // possible values GRAVITY_TEXT_START, GRAVITY_TEXT_END and GRAVITY_CENTER
        gravity = DrawableButton.GRAVITY_TEXT_END  // default value is GRAVITY_TEXT_END
        
        textMarginRes = R.dimen.progressMargin //margin between text and drawable. default 10dp
        textMarginPx = 30 //margin between text and drawable in pixels. default 10dp
    }

    // Hide progress and show "Save" text instead
    myButton.hideDrawable(R.string.save)
}
```
