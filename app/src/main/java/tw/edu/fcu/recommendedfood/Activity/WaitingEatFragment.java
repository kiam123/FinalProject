package tw.edu.fcu.recommendedfood.Activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tw.edu.fcu.recommendedfood.Adapter.WaitingEatAdapter;
import tw.edu.fcu.recommendedfood.Data.WaitingEatColors;
import tw.edu.fcu.recommendedfood.Data.WaitingEatData;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.WaitingEatDBItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaitingEatFragment extends Fragment {

    private ListView listView;

    // ListView使用的自定Adapter物件
    private WaitingEatAdapter waitingEatAdapter;
    // 儲存所有記事本的List物件
    private List<WaitingEatData> waitingEatDatas;

    // 選單項目物件
    private MenuItem add_item, search_item, revert_item, delete_item;
    private ImageView imgAddItem, imgSearchItem, imgRevertItem, imgDeleteItem;

    // 已選擇項目數量
    private int selectedCount = 0;

    private WaitingEatDBItem waitingEatDBItem;

    Toolbar toolbar;
    LinearLayout search;

    public WaitingEatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_waiting_eat, container, false);

        toolbar = (Toolbar) viewGroup.findViewById(R.id.fragment_toolbar);

        setHasOptionsMenu(true);//fragment在需要使用這個method來讓onCreateOptionsMenu生效
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);//隱藏action bar title


        initViews(viewGroup);
//        initOptionsMenu(viewGroup);
        initControllers();
        initAdapter();
        return viewGroup;
    }

    private void initViews(ViewGroup viewGroup) {
        listView = (ListView) viewGroup.findViewById(R.id.item_list);
        search = (LinearLayout) viewGroup.findViewById(R.id.search);
    }

    public void initAdapter() {
        waitingEatDBItem = new WaitingEatDBItem(getActivity());

//        if(waitingEatDBItem.getCount()==0){
//            waitingEatDBItem.sample();
//        }
        //取得所有記事資料
        waitingEatDatas = waitingEatDBItem.getAll();
        // 建立自定Adapter物件
        waitingEatAdapter = new WaitingEatAdapter(getActivity(), R.layout.layout_waiting_eat_singleitem, waitingEatDatas);
        listView.setAdapter(waitingEatAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 如果被啟動的Activity元件傳回確定的結果
        if (resultCode == Activity.RESULT_OK) {
            // 讀取記事物件
            WaitingEatData waitingeatdata = (WaitingEatData) data.getExtras().getSerializable(
                    "com.example.yan.die_eat.WaitingEatData");

            // 如果是新增記事
            if (requestCode == 0) {
                waitingeatdata = waitingEatDBItem.insert(waitingeatdata);
                waitingEatDatas.add(waitingeatdata);
                // 通知資料改變
                waitingEatAdapter.notifyDataSetChanged();
            }
            // 如果是修改記事
            else if (requestCode == 1) {
                // 讀取記事編號
                int position = data.getIntExtra("position", -1);

                if (position != -1) {
                    // 修改資料庫的記事
                    waitingEatDBItem.update(waitingeatdata);
                    waitingEatDatas.set(position, waitingeatdata);
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

        //建立長按監聽物件
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

        //註冊長按監聽物件
        //   showAppName.setOnLongClickListener(listener);
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

        add_item.setVisible(selectedCount == 0);
        search_item.setVisible(selectedCount == 0);
        revert_item.setVisible(selectedCount > 0);
        delete_item.setVisible(selectedCount > 0);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.waitting_eat_menu, menu);

        // 取得選單項目物件
        add_item = menu.findItem(R.id.img_add_item);
        search_item = menu.findItem(R.id.img_search_item);
        revert_item = menu.findItem(R.id.img_revert_item);
        delete_item = menu.findItem(R.id.img_delete_item);
        delete_item = menu.findItem(R.id.img_delete_item);
        // 設定選單項目
        processMenu(null);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 使用參數取得使用者選擇的選單項目元件編號
        int itemId = item.getItemId();

        // 判斷該執行什麼工作，目前還沒有加入需要執行的工作
        switch (itemId) {
            case R.id.img_search_item:
                Intent searchIntent = new Intent();
                searchIntent.setClass(getActivity(), WaitingEatSearchActivity.class);
                startActivity(searchIntent);
                break;
            // 使用者選擇新增選單項目
            case R.id.img_add_item:
                // 使用Action名稱建立啟動另一個Activity元件需要的Intent物件
                Intent intent = new Intent("com.example.yan.die_eat.ADD_ITEM");
                // 呼叫「startActivityForResult」，，第二個參數「0」表示執行新增
                startActivityForResult(intent, 0);
                break;
            // 取消所有已勾選的項目
            case R.id.img_revert_item:
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
            case R.id.img_delete_item:
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
                                        waitingEatDBItem.delete(item.getId());
                                    }

                                    index--;
                                }

                                // 通知資料改變
                                waitingEatAdapter.notifyDataSetChanged();

                                //     processMenu(null);
                            }
                        });
                d.setNegativeButton(android.R.string.no, null);
                d.show();

                break;
        }
        return false;
    }

    public void onResume() {
        super.onResume();
    }
}
