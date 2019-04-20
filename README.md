# Generic-RecyclerView [![Release](https://jitpack.io/v/canozgen9/Generic-RecyclerView.svg)](https://jitpack.io/#canozgen9/Generic-RecyclerView)

An android library that generifies recycler view

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
```java
public class Item1 implements GenericRecyclerItem {
    public String text;
}
```

### Creating view holders layout.
Create layout for view holder.
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
	recyclerView.add(new Item2("Item 2 " + i));
	recyclerView.add(new Item3("Item 3 " + i, "Desc"));
}
```

