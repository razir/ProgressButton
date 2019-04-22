# Progress Button Android      [ ![Download](https://api.bintray.com/packages/razir/maven/progressbutton/images/download.svg?version=1.0.1) ](https://bintray.com/razir/maven/progressbutton/1.0.1/link) 

![basic progress button example](https://raw.githubusercontent.com/razir/ProgressButton/master/gif/progress_default.gif) ![progress cebter button example](https://raw.githubusercontent.com/razir/ProgressButton/master/gif/progress_center.gif)
![mixed progress button example](https://raw.githubusercontent.com/razir/ProgressButton/master/gif/mixed.gif)  

#### Add progress to any button by few lines of code without layout changes

### Main features: 
  - No layout changes required
  - Few lines of code to add
  - Easy configurable
  - Customizable 
  - Built in fade animations

## Gradle dependency 

    implementation 'com.github.razir.progressbutton:progressbutton:1.0.1'

## How to use

### Basic example

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    // bind your button to activity lifecycle
    bindProgressButton(myButton)

    // (Optional) Enable fade In / Fade out animations 
    myButton.attachTextChangeAnimator()

    // Show progress with "Loading" text
    myButton.showProgress {
        buttonTextRes = R.string.loading
        progressColor = Color.WHITE
    }

    // Hide progress and show "Submit" text instead
    myButton.hideProgress(R.string.submit)
}
```
You can find the full config here: 


### Showing AnimatedDrawable

![animated drawable button example](https://raw.githubusercontent.com/razir/ProgressButton/feature/release-1.0.0/gif/animated_drawable.gif)

```kotlin
val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)  
//Defined bounds are required for your drawable  
animatedDrawable.setBounds(0, 0, 40, 40)  
  
button.showDrawable(animatedDrawable) {  
  buttonTextRes = R.string.saved  
}
```

### Avoiding memory leaks
To avoid memory leaks you always need to bind your button to a LifecycleOwner (usually Activity, or Fragment) :

```kotlin
[LifecycleOwner].bindProgressButton(button)
```

### License 
Apache 2.0

### Author
Anton Hadutski
