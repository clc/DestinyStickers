This Destiny Stickers app uses Android's [Storage Access Framework](https://developer.android.com/guide/topics/providers/document-provider.html) to provide Destiny stickers to other Android apps such as the default Messages SMS app, Hangouts, PS Messages, etc.

The stickers themselves are from [Kevin Raganit](http://kevinraganit.com); I'm just trying to make it easier for fellow Android Guardians to enjoy the awesomeness. :)

I'm releasing the source code under APACHE 2.0; [here's a precompiled APK for convenience](DestinyStickers.apk).

After you install this app, you can get Destiny stickers in other apps by following these steps:

1. In your chat app, click on whatever icon is used to trigger the standard Android file picker. Typically, this is the attach file/gallery icon button. See the screenshots below for examples of where this is in Messages, Hangouts, and PS Messages.
![Messages](screenshots/01_messages.png?raw=true)
![Hangouts](screenshots/01_hangouts.png?raw=true)
![PS Messages](screenshots/01_psmessages.png?raw=true)

2. Once you bring up the standard file picker, click on the menu button on the top left.
![Menu button](screenshots/02_menubutton.png?raw=true)

3. You'll now see "Destiny" as an option. Click on that.
![Destiny](screenshots/03_destiny.png?raw=true)

4. Pick the sticker you want.
![Stickers](screenshots/04_stickers.png?raw=true)

5. Voila, you can now send it in your chat app! :)

6. ???

7. PROFIT!!


Hat tip to ianhanniballake whose excellent [LocalStorage project](https://github.com/ianhanniballake/LocalStorage) and [Medium post](https://medium.com/google-developers/building-a-documentsprovider-f7f2fb38e86a) were great tutorials for learning how to create a custom DocumentsProvider.


