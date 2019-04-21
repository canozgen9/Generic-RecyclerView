# Generic-RecyclerView [![Release](https://jitpack.io/v/canozgen9/Generic-RecyclerView.svg)](https://jitpack.io/#canozgen9/Generic-RecyclerView)

Generic-RecyclerView is an android library that generifies recycler view.

## Getting Started
### Setting up dependencies
In your **build.gradle** (project level) add following lines.
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

In your **build.gradle** (module level) add following lines.
```gradle
dependencies {
    implementation 'com.github.canozgen9:Generic-RecyclerView:0.1.0'
}

android {
    ...
    // Java 8 support
    compileOptions {
        sourceCompatibility = '1.8'
        targetCompatibility = '1.8'
    }
}
```

### Adding to the layout
Add **GenericRecyclerView** to your layout.
```xml
 <com.canozgen.genericrv.GenericRecyclerView
        android:id="@+id/RecyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
### Creating generic items
Simply create your item class. Then implement **GenericRecyclerItem** interface.

**Item1.java**
```java
public class Item1 implements GenericRecyclerItem {
    public String text;
    
    public Item1(String text) {
        this.text = text;
    }
}
```

### Creating view holders layout
Create layout for view holder.

**holder_1.xml**
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <TextView
        android:id="@+id/TextView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="20dp"
        android:textColor="#202020" />
</LinearLayout>
```

### Creating generic view holder
Create a class which extends from **GenericViewHolder** depending on your item.
>  Don't forget to create **GenericRecyclerItem** before creating **GenericViewHolder**.

**Holder1.java**
```java
public class Holder1 extends GenericViewHolder<Item1> {
    // Define your views
    private TextView textView;

    // You don't need to worry about constructor.
    public Holder1(@NonNull View itemView, ViewHolderClickEventListener clickEventListener) {
        super(itemView, clickEventListener);
    }
    // Initialize your views. 
    @Override public void init() {
        textView = (TextView) init(R.id.TextView);
    }
    // Assign click listeners.
    @Override public void assign() {
        assignClickEvent(textView);
    }
    // Bind your item to the view.
    @Override public void bind(Item1 item, int position) {
        textView.setText(item.text);
    }
}
```

### All done!
Now, in your Activity or Fragment, you can simply initialize your **GenericRecyclerView**.

```java
// Find your GenericRecyclerView
GenericRecyclerView<GenericRecyclerItem> recyclerView = findViewById(R.id.RecyclerView);

// Use init() method to build it
recyclerView.init(RecyclerActivity.this)
            .listener((item, position, clickEventCode) -> {
                Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
            })
            .layout().linear()
            .addView().item(Item1.class).holder(Holder1.class).layout(R.layout.holder_1).span(1)
            .build();

// Create items and add
for (int i = 0; i < 50; i++) {
	recyclerView.add(new Item1("Item 1 " + i));
}
```
## Documentation
### Constructuring
##### init(Context context)
>returns GenericRecyclerView's itself.

##### listener(GenericRecyclerClickListener<T> recyclerClickListener)
>returns GenericRecyclerView's itself.

##### layout()
>Use `layout().grid()` or `layout().linear()` and see the options below. After all call `addView()` to begin adding view holders.

###### layout().grid()
| method | params | default | returns | description |
|:--|:--|:--|:--|:--|
|orientation()|GenericOrientation|VERTICAL|itself|Sets the orientation of grid layout. `GenericOrientation.VERTICAL`, `GenericOrientation.HORIZONTAL`|
|reverse()|boolean|false|itself|Sets the reverse ordering.|
|span()|int|1|itself|Sets the total span count of one row.|
|addView()|-|-|GenericViewHolderBuilder|Starts to add view holders.|

Example
```java
...
layout()
    .grid() // start
    .orientation(GenericOrientation.VERTICAL) // optional
    .reverse(false) // optional
    .span(6) // optional
    .addView() // end
    ....
```

###### layout().linear()
| method | params | default | returns | description |
|:--|:--|:--|:--|:--|
|orientation()|GenericOrientation|VERTICAL|itself|Sets the orientation of grid layout. `GenericOrientation.VERTICAL`, `GenericOrientation.HORIZONTAL`|
|reverse()|boolean|false|itself|Sets the reverse ordering.|
|decoration()|GenericDecoration|NONE|itself|Adds the decoration. `GenericDecoration.NONE`, `GenericDecoration.DARK`, `GenericDecoration.LIGHT`|
|addView()|-|-|GenericViewHolderBuilder|Starts to add view holders.|

Example
```java
...
layout()
    .linear() // start
    .orientation(GenericOrientation.VERTICAL) // optional
    .reverse(false) // optional
    .decoration(GenericDecoration.DARK) // optional
    .addView() // end
    ....
```

##### addView()
| method | params | default | returns | description |
|:--|:--|:--|:--|:--|
|holder()|Class|REQUIRED|itself|Sets the class of holder which you created before.|
|item()|Class|REQUIRED|itself|Sets the class of item associated with your holder.|
|layout()|int|REQUIRED|itself|Sets the layout id of your view holder.|
|span()|int|1|itself|Span count of item. Only works if **grid layout** is selected.|
|addView()|-|-|GenericViewHolderBuilder|Starts to add more view holders.|
|build()|-|-|GenericRecyclerView|Ends the build process.|

Example
```java
...
.addView() // start
.item(ItemFoo.class) // item class
.holder(HolderFoo.class) // holder class
.layout(R.layout.holder_foo) // holder resource
.span(1) // span count (If grid layout is selected)

.addView() // start
.item(ItemBar.class) // another item class
.holder(HolderBar.class) // another holder class
.layout(R.layout.holder_bar) // another holder resource
.span(3) // span count
.build() // finally build
```
### Items
| method | params | description |
|:--|:--|:--|
|set()|ArrayList&lt;T&gt;| Sets the items|
|add()|ArrayList&lt;T&gt;|Adds the items|
|add()|T|Adds the item|
|remove()|-|Clears the items|
|remove()|int|Removes the item at given index|
|remove()|T|Removes the item|
|update()|T|Updates the given item|
|getItems()|ArrayList&lt;T&gt;|Returns all items|








### TODO List
- Add more layout options.
- Document **GenericViewHolder** class.
- Prepare contributing guide.
