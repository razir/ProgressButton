# Progress Button Android (In Progress)

![enter image description here](https://raw.githubusercontent.com/razir/ProgressButton/feature/release-1.0.0/gif/progress_default.gif)

#### Add progress to any button by few lines of code without layout changes

### Main features: 
  - No layout changes required
  - Few lines of code to add
  - Easy configurable
  - Customizable 
  - Built in fade animations

## Gradle dependency 

    implementation 'com.github.razir.progressbutton:progressbutton:1.0.0'

## How to use

### Basic example

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            // bind your button to activity lifecycle
            bindProgressButton(myButton)
            
            // (Optional) Enable fade In / Fade out animations 
            buttonProgressRightText.attachTextChangeAnimator()
            
            // Show progress with "Loading" text
            button.showProgress {
                buttonTextRes = R.string.loading
                progressColor = Color.WHITE
            }
            
            // Hide progress and show "Submit" text instead
            button.hideProgress(R.string.submit)
        }


