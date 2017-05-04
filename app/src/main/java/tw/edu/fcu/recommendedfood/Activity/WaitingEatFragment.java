package tw.edu.fcu.recommendedfood.Activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tw.edu.fcu.recommendedfood.Adapter.WaitingEatAdapter;
import tw.edu.fcu.recommendedfood.Data.WaitingEatColors;
import tw.edu.fcu.recommendedfood.Data.WaitingEatData;
import tw.edu.fcu.recommendedfood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaitingEatFragment extends Fragment {

    private ListView listView;
    private TextView showAppName;

    // 刪除原來的宣告
    //private ArrayList<String> data = new ArrayList<>();
    //private ArrayAdapter<String> adapter;

    // ListView使用的自定Adapter物件
    private WaitingEatAdapter waitingEatAdapter;
    // 儲存所有記事本的List物件
    private List<WaitingEatData> waitingEatDatas;

    // 選單項目物件
    private MenuItem menuAddItem, menuSearchItem, menuRevertItem, menuDeleteItem;

    // 已選擇項目數量
    private int selectedCount = 0;

    public WaitingEatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup =(ViewGroup)inflater.inflate(R.layout.fragment_waiting_eat, container, false);

        initViews(viewGroup);
        initAdapter();
        initControllers();

        return viewGroup;
    }

    private void initViews(ViewGroup viewGroup) {
        listView = (ListView) viewGroup.findViewById(R.id.item_list);
        //    showAppName = (TextView) findViewById(R.id.showAppName);
    }

    public void initAdapter(){
        // 加入範例資料
        waitingEatDatas = new ArrayList<WaitingEatData>();

        waitingEatDatas.add(new WaitingEatData(1, new Date().getTime(), WaitingEatColors.RED, "吃KFC", "想吃蛋塔", "", "", 0, 0, 0));
        waitingEatDatas.add(new WaitingEatData(2, new Date().getTime(), WaitingEatColors.BLUE, "麥當勞", "勁辣雞腿堡", "", "", 0, 0, 0));
        waitingEatDatas.add(new WaitingEatData(3, new Date().getTime(), WaitingEatColors.GREEN, "香香滷味", "便宜好吃", "", "", 0, 0, 0));

        // 建立自定Adapter物件
        waitingEatAdapter = new WaitingEatAdapter(getActivity(), R.layout.layout_waiting_eat_singleitem, waitingEatDatas);
        listView.setAdapter(waitingEatAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 如果被啟動的Activity元件傳回確定的結果
        if (resultCode == Activity.RESULT_OK) {
            // 讀取記事物件
            WaitingEatData item = (WaitingEatData) data.getExtras().getSerializable(
                    "com.example.yan.die_eat.WaitingEatData");

            // 如果是新增記事
            if (requestCode == 0) {
                // 設定記事物件的編號與日期時間
                item.setId(waitingEatDatas.size() + 1);
                item.setDatetime(new Date().getTime());

                // 加入新增的記事物件
                waitingEatDatas.add(item);

                // 通知資料改變
                waitingEatAdapter.notifyDataSetChanged();
            }
            // 如果是修改記事
            else if (requestCode == 1) {
                // 讀取記事編號
                int position = data.getIntExtra("position", -1);

                if (position != -1) {
                    // 設定修改的記事物件
                    waitingEatDatas.set(position, item);
                    waitingEatAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void initControllers() {
        // 建立選單項目點擊監聽物件
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 讀取選擇的記事物件
                WaitingEatData item = waitingEatAdapter.getItem(position);

                // 如果已經有勾選的項目
                if (selectedCount > 0) {
                    // 處理是否顯示已選擇項目
                    processMenu(item);
                    // 重新設定記事項目
                    waitingEatAdapter.set(position, item);
                } else {
                    Intent intent = new Intent(
                            "com.example.yan.die_eat.EDIT_ITEM");

                    // 設定記事編號與記事物件
                    intent.putExtra("position", position);
                    intent.putExtra("com.example.yan.die_eat.WaitingEatData", item);

                    startActivityForResult(intent, 1);
                }
            }
        };

        // 註冊選單項目點擊監聽物件
        listView.setOnItemClickListener(itemListener);

        // 建立記事項目長按監聽物件
        AdapterView.OnItemLongClickListener itemLongListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // 讀取選擇的記事物件
                WaitingEatData item = waitingEatAdapter.getItem(position);
                // 處理是否顯示已選擇項目
                processMenu(item);
                // 重新設定記事項目
                waitingEatAdapter.set(position, item);
                return true;
            }
        };

        // 註冊記事項目長按監聽物件
        listView.setOnItemLongClickListener(itemLongListener);

        // 建立長按監聽物件
        View.OnLongClickListener listener = new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder dialog =
                        new AlertDialog.Builder(getActivity());
                dialog.setTitle(R.string.app_name)
                        .setMessage(R.string.about)
                        .show();
                return false;
            }

        };

        // 註冊長按監聽物件
        //  showAppName.setOnLongClickListener(listener);
    }

    // 處理是否顯示已選擇項目
    private void processMenu(WaitingEatData item) {
        // 如果需要設定記事項目
        if (item != null) {
            // 設定已勾選的狀態
            item.setSelected(!item.isSelected());

            // 計算已勾選數量
            if (item.isSelected()) {
                selectedCount++;
            } else {
                selectedCount--;
            }
        }

        // 根據選擇的狀況，設定是否顯示選單項目
        menuAddItem.setVisible(selectedCount == 0);
        menuSearchItem.setVisible(selectedCount == 0);
        menuRevertItem.setVisible(selectedCount > 0);
        menuDeleteItem.setVisible(selectedCount > 0);
    }

    // 載入選單資源
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.waiting_eat_menu_main, menu);

        // 取得選單項目物件
        menuAddItem = menu.findItem(R.id.menu_add_item);
        menuSearchItem = menu.findItem(R.id.menu_search_item);
        menuRevertItem = menu.findItem(R.id.menu_revert_item);
        menuDeleteItem = menu.findItem(R.id.menu_delete_item);

        // 設定選單項目
        processMenu(null);

    }

    // 使用者選擇所有的選單項目都會呼叫這個方法
    public void clickMenuItem(MenuItem item) {
        // 使用參數取得使用者選擇的選單項目元件編號
        int itemId = item.getItemId();

        // 判斷該執行什麼工作，目前還沒有加入需要執行的工作
        switch (itemId) {
            case R.id.menu_search_item:
                break;
            // 使用者選擇新增選單項目
            case R.id.menu_add_item:
                // 使用Action名稱建立啟動另一個Activity元件需要的Intent物件
                Intent intent = new Intent("com.example.yan.die_eat.ADD_ITEM");
                // 呼叫「startActivityForResult」，，第二個參數「0」表示執行新增
                startActivityForResult(intent, 0);
                break;
            // 取消所有已勾選的項目
            case R.id.menu_revert_item:
                for (int i = 0; i < waitingEatAdapter.getCount(); i++) {
                    WaitingEatData ri = waitingEatAdapter.getItem(i);

                    if (ri.isSelected()) {
                        ri.setSelected(false);
                        waitingEatAdapter.set(i, ri);
                    }
                }

                selectedCount = 0;
                processMenu(null);

                break;
            // 刪除
            case R.id.menu_delete_item:
                // 沒有選擇
                if (selectedCount == 0) {
                    break;
                }

                // 建立與顯示詢問是否刪除的對話框
                AlertDialog.Builder d = new AlertDialog.Builder(getActivity());
                String message = getString(R.string.delete_item);
                d.setTitle(R.string.delete)
                        .setMessage(String.format(message, selectedCount));
                d.setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 刪除所有已勾選的項目
                                int index = waitingEatAdapter.getCount() - 1;

                                while (index > -1) {
                                    WaitingEatData item = waitingEatAdapter.get(index);

                                    if (item.isSelected()) {
                                        waitingEatAdapter.remove(item);
                                    }

                                    index--;
                                }

                                // 通知資料改變
                                waitingEatAdapter.notifyDataSetChanged();
                                selectedCount = 0;
                                processMenu(null);
                            }
                        });
                d.setNegativeButton(android.R.string.no, null);
                d.show();

                break;
        }
    }

    // 點擊應用程式名稱元件後呼叫的方法
    public void aboutApp(View view) {
        // 建立啟動另一個Activity元件需要的Intent物件
        // 建構式的第一個參數：「this」
        // 建構式的第二個參數：「Activity元件類別名稱.class」
        Intent intent = new Intent(getActivity(), WaitingEatAboutActivity.class);
        // 呼叫「startActivity」，參數為一個建立好的Intent物件
        // 這行敘述執行以後，如果沒有任何錯誤，就會啟動指定的元件
        startActivity(intent);
    }

}
