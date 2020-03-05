# Progress Button Android  [ ![Download](https://api.bintray.com/packages/razir/maven/progressbutton/images/download.svg?version=2.0.1) ](https://bintray.com/razir/maven/progressbutton/2.0.1/link)

![basic progress button example](https://raw.githubusercontent.com/razir/ProgressButton/master/gif/progress_default.gif) ![progress cebter button example](https://raw.githubusercontent.com/razir/ProgressButton/master/gif/progress_center.gif)
![mixed progress button example](https://raw.githubusercontent.com/razir/ProgressButton/master/gif/mixed.gif)  

#### Article on ProAndroidDev.com explaining how it works
https://proandroiddev.com/replace-progressdialog-with-a-progress-button-in-your-app-14ed1d50b44

#### Add progress to any button by few lines of code without layout changes

### Main features: 
  - No layout changes required
  - Few lines of code to add
  - Easy configurable
  - Customizable 
  - Built in fade animations

## Gradle dependency 
```
allprojects {
  repositories {
    jcenter()
  }
}
```
    
```
implementation 'com.github.razir.progressbutton:progressbutton:2.0.1'
```

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

### Showing AnimatedDrawable

![animated drawable button example](https://raw.githubusercontent.com/razir/ProgressButton/master/gif/animated_drawable.gif)

```kotlin
val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)  
//Defined bounds are required for your drawable  
animatedDrawable.setBounds(0, 0, 40, 40)  
  
button.showDrawable(animatedDrawable) {  
  buttonTextRes = R.string.saved  
}
```

### Detailed doc: [here](DetailedDoc.md)

### Min SDK 14

### Avoiding memory leaks
To avoid memory leaks you always need to bind your button to a LifecycleOwner (usually Activity, or Fragment) :

```kotlin
[LifecycleOwner].bindProgressButton(button)
```

### License 
Apache 2.0

### Author
Anton Hadutski 
