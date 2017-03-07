package la.baibu.youwoexample.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import la.baibu.youwoexample.MainActivity;
import la.baibu.youwoexample.R;
import la.baibu.youwoexample.adapter.WordAdatper;
import la.baibu.youwoexample.bean.Person;
import la.baibu.youwoexample.view.WordsNavigationView;

/**
 * Created by minna_Zhou on 2016/11/23 0023.
 * 首页的第二个fragment：联系人列表自定义
 */
public class SecondFragment extends Fragment implements WordsNavigationView.onWordsChangeListener, AbsListView.OnScrollListener {
    private static int position;
    private MainActivity mainActivity;
    private TextView tv;
    private WordsNavigationView word;
    private Handler handler = new Handler();
    private List<Person> personList;
    private ListView listview;
    private WordAdatper mAdatper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_contacts_list, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv = (TextView) view.findViewById(R.id.tv);
        listview = (ListView) view.findViewById(R.id.listview);
        word = (WordsNavigationView) view.findViewById(R.id.words);
        word.setOnWordsChangeListener(this);
        initData();
        mAdatper = new WordAdatper(getContext(), personList);
        listview.setAdapter(mAdatper);
        listview.setOnScrollListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static Fragment newInstance(int position) {
        SecondFragment.position = position;
        SecondFragment homeFragment = new SecondFragment();
        return homeFragment;
    }

    @Override
    public void wordsChange(String words) {
        updateWord(words); //手指按下字母改变监听回调
        updateListView(words);
    }

    /**
     * 更新中央的字母提示
     *
     * @param words 首字母
     */
    private void updateWord(String words) {
        tv.setText(words);
        tv.setVisibility(View.VISIBLE);
        //清空之前的所有消息
        handler.removeCallbacksAndMessages(null);
        //500ms后让tv隐藏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setVisibility(View.GONE);
            }
        }, 500);
    }


    /**
     * 初始化联系人列表信息
     */

    private void initData() {
        personList = new ArrayList<>();
        personList.add(new Person("Dave"));
        personList.add(new Person("阿钟"));
        personList.add(new Person("阿彬"));
        personList.add(new Person("阿吃"));
        personList.add(new Person("阿的"));
        personList.add(new Person("吧的"));
        personList.add(new Person("把的1"));
        personList.add(new Person("吧的2"));
        personList.add(new Person("吧的3"));
        personList.add(new Person("吧的4"));
        personList.add(new Person("吧的5"));

        personList.add(new Person("胡继群"));
        personList.add(new Person("隔壁老王"));
        personList.add(new Person("姜宇航1"));
        personList.add(new Person("姜宇航2"));
        personList.add(new Person("姜宇航3"));
        personList.add(new Person("姜宇航4"));


        personList.add(new Person("谭永新1"));
        personList.add(new Person("谭永新2"));
        personList.add(new Person("谭永新3"));
        personList.add(new Person("谭永新4"));
        personList.add(new Person("谭永新5"));
        personList.add(new Person("谭永新6"));
        personList.add(new Person("刘程1"));
        personList.add(new Person("刘程2"));
        personList.add(new Person("刘程3"));
        personList.add(new Person("刘程4"));
        personList.add(new Person("刘程5"));
        personList.add(new Person("刘程6"));
        personList.add(new Person("周敏霞1"));
        personList.add(new Person("周敏霞2"));
        personList.add(new Person("周敏霞3"));
        personList.add(new Person("周敏霞4"));
        personList.add(new Person("周敏霞5"));
        personList.add(new Person("周敏霞6"));
        //对集合排序
        Collections.sort(personList, new Comparator<Person>() {
            @Override
            public int compare(Person lhs, Person rhs) {
                //根据拼音进行排序
                return lhs.getPinyin().compareTo(rhs.getPinyin());
            }
        });
    }

    /**
     * @param words 首字母
     */
    private void updateListView(String words) {
        for (int i = 0; i < personList.size(); i++) {
            String headerWord = personList.get(i).getHeaderWord();
            //将手指按下的字母与列表中相同字母开头的项找出来
            if (words.equals(headerWord)) {
                //将列表选中哪一个
                listview.setSelection(i);
                //找到开头的一个即可
                return;
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//当滑动列表的时候，更新右侧字母列表的选中状态
        word.setTouchIndex(personList.get(firstVisibleItem).getHeaderWord());
    }
}
