# CookPadMobile
#### App that access a large number of DIY recipes.

When running the App It would open on the Collections Screen, where you ca see the Themes of the Recipes within.
Also from the main screen you can see on the bottom navigation menu the Recipes. This screen shows a number of recipes, all mixed up without a specific theme.
If you want to search by them I'd suggest to pick from the Collections Screen, If you just want some Recipe without a specific theme in mind, use the Recipes screen.

##### To go into detail click an item from any of those lists:
- An item from Collections, when clicked, shows a BottomSheetDialogFragment with the Recipes from that Collection. In this screen you can also click on a Recipe and It opens another screen with the Recipe Detail.
- An item from Recipes, when clicked has exactly the same behaviour that I've explained before.

##### Future of the APP:
- I would implement the Tests, Ui and Unit Tests. Due to the time frame that I had available and the experience that I have implementing tests, I had to choose between implement the rest or take way longer on this. Still, I would like to take the time to implement!!
- I would have a different approach to the Ui, for instance, on the Collections Screen the opening of a List of Recipes from a specific collection It's Ok, but... then opening another one for the Detail, I would rather implement or a unique fragment with horizontal navigation, or try another approach.
  Probably this updates or, taking this into account would impact the architecture and the overall implementation.

## Minimum Requirements:
**This APP needs internet access, so there's no Offline Mode implemented.
The App runs from the Android Studio into a real device or an emulator.
The minimum Api is 23 (Android 6.0 Marshmallow).**
