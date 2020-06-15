package com.example.databasemodule.Bluetooth.bluetoothfragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.system.Int64Ref;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.example.databasemodule.Bluetooth.common.logger.Log;
import com.example.databasemodule.R;
import com.example.databasemodule.Views.emulator.GetActivity;
import com.example.databasemodule.Views.frontEnd.DateTimePicker;
import com.example.databasemodule.Views.frontEnd.GetActivityView;
import com.example.databasemodule.Views.frontEnd.Units;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

public class GetFragment extends Fragment {

    private static final String TAG = "GetFragment";

    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    private Button mButtonSend;
    private TextView mTextView;
    private Spinner mSpinner;
    private EditText mTsStartEditText;
    private EditText mTsStopEditText;
    private ObjectMapper objectMapper;
    private StringBuilder stringBuilder = new StringBuilder();

    private String mConnectedDeviceName = null;
    private DateTimePicker dateTimePicker;

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            final FragmentActivity activity = getActivity();
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            mTextView.setText("");
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    stringBuilder.append(readMessage);
                    if(readMessage.endsWith("}]}"))
                    mTextView.setText(deserializeResponse(stringBuilder.toString()));
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != activity) {
                        Toast.makeText(activity, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    if (null != activity) {
                        Toast.makeText(activity, msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    private StringBuffer mOutStringBuffer;
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothService bluetoothService = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        objectMapper = new ObjectMapper();
        dateTimePicker = new DateTimePicker();

        if (mBluetoothAdapter == null) {
            final FragmentActivity activity = getActivity();
            Toast.makeText(activity, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mBluetoothAdapter.isEnabled()) {
            final Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else if (bluetoothService == null) {
            setupChat();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bluetoothService != null) {
            bluetoothService.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (bluetoothService != null) {
            if (bluetoothService.getState() == BluetoothService.STATE_NONE) {
                bluetoothService.start();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_get_emulator, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mButtonSend = view.findViewById(R.id.get_button_send);
        mTextView = view.findViewById(R.id.get_text_view_id);
        mSpinner = view.findViewById(R.id.get_spinner_variable_id);
        mTsStartEditText = view.findViewById(R.id.get_ts_start_id);
        mTsStopEditText = view.findViewById(R.id.get_ts_stop_id);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getContext(), R.array.variables,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mTsStartEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimePicker.showDateTimeDialog(mTsStartEditText, (GetActivityView)getActivity());
            }
        });
        mTsStartEditText.setKeyListener(null);

        mTsStopEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimePicker.showDateTimeDialog(mTsStopEditText, (GetActivityView)getActivity());
            }
        });
        mTsStopEditText.setKeyListener(null);
    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        bluetoothService = new BluetoothService(getActivity(), mHandler);

        mOutStringBuffer = new StringBuffer();

        mButtonSend.setOnClickListener(v -> {
            View view = getView();
            if (null != view) {
                final String variable = mSpinner.getSelectedItem().toString();
                final long ts_start = dateTimePicker.getTimestamp(mTsStartEditText);
                final long ts_stop  = dateTimePicker.getTimestamp(mTsStopEditText);
                sendMessage(prepareJson(variable, ts_start, ts_stop));
            }
        });
    }

    private String prepareJson(final String variable, final long ts_start, final long ts_stop) {
        final ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("command", "get");

        final ObjectNode delParams = objectMapper.createObjectNode();
        delParams.put("variable", variable);
        delParams.put("ts_start", ts_start);
        delParams.put("ts_stop", ts_stop);

        rootNode.set("get_params", delParams);

        try {
            mTextView.setText(objectMapper.writer().writeValueAsString(rootNode));
            return objectMapper.writer().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            final Intent discoverableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    private void sendMessage(String message) {
        if (bluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(getActivity(), R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        if (message.length() > 0) {
            byte[] send = message.getBytes();
            bluetoothService.write(send);

            mOutStringBuffer.setLength(0);
        }
    }

    private void setStatus(int resId) {
        final FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(resId);
    }

    private void setStatus(CharSequence subTitle) {
        final FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(subTitle);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    setupChat();
                } else {
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(getActivity(), R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
        }
    }

    private void connectDevice(final Intent data, final boolean secure) {
        final String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        bluetoothService.connect(device, secure);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.bluetooth_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.secure_connect_scan: {
                final Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                return true;
            }
            case R.id.insecure_connect_scan: {
                final Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
                return true;
            }
            case R.id.discoverable: {
                ensureDiscoverable();
                return true;
            }
        }
        return false;
    }

    private String deserializeResponse(String Json){
        String out = "";
        stringBuilder.setLength(0);
        try{
            JSONObject jsonObj = new JSONObject(Json);
            JSONArray variables = jsonObj.getJSONArray("values");

                out += Units.nameMap.get(mSpinner.getSelectedItem().toString()) + "\n";
                for(int i = 0; i < variables.length(); i++){
                    JSONObject v = variables.getJSONObject(i);
                    long timestamp = v.getLong("ts");
                    double value = v.getDouble("value");
                    out += dateTimePicker.timestampToString(timestamp) + " " + value +
                            Units.unitsMap.get(mSpinner.getSelectedItem().toString()) + "\n";
                }
        } catch (JSONException e){
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
        return out;
    }

    private long getTimestamp(EditText textView){
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
        Date date = new Date();
        if(textView.getText().toString().equals("Od")){
          return 0;
        } else if(textView.getHint().toString().equals("Do")){
            date.setTime(currentTimeMillis());
            return date.getTime();
        } else {
            try {
                date = simpleDateFormat.parse(textView.getText().toString());
                return date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
