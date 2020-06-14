package com.example.databasemodule.Bluetooth.bluetoothfragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.example.databasemodule.Bluetooth.common.logger.Log;
import com.example.databasemodule.R;
import com.example.databasemodule.Views.emulator.GetActivity;
import com.example.databasemodule.Views.frontEnd.SetActivityView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SetFragment extends Fragment {

    private static final String TAG = "SetFragment";

    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    private static final Map<String, String> hashValues = new HashMap<String, String>(){
        {
            put("Temperatura", "TEMP_F");
            put("Wilgotność", "HUM_F");
            put("Ciśnienie", "PRESS_F");
            put("Dane energetyczne", "ENERGY_F");
        }
    };

    private Button mButtonSend;
    private RadioGroup mRadioGroup;
    private RadioButton radioButton;
    private EditText mValueEditText;
    private ObjectMapper objectMapper;

    private String mConnectedDeviceName = null;

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
                    showResponse(readMessage);
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
        return inflater.inflate(R.layout.fragment_set_emulator, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mButtonSend = view.findViewById(R.id.set_button_send);
        mValueEditText = view.findViewById(R.id.set_value_id);
        mRadioGroup = view.findViewById(R.id.radioGroup);
       /* final ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getContext(), R.array.variables_configurable,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);*/
    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        bluetoothService = new BluetoothService(getActivity(), mHandler);

        mOutStringBuffer = new StringBuffer();

        mButtonSend.setOnClickListener(v -> {
            View view = getView();
            if (null != view) {
                int radioId = mRadioGroup.getCheckedRadioButtonId();
                radioButton = view.findViewById(radioId);
                final String variable = hashValues.get(radioButton.getText().toString());
                final int value = Integer.parseInt(mValueEditText.getText().toString());

                sendMessage(prepareJson(variable, value));
            }
        });
    }

    private String prepareJson(final String variable, final int value) {
        final ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("command", "set");

        final ObjectNode delParams = objectMapper.createObjectNode();
        delParams.put("variable", variable);
        delParams.put("value", value);

        rootNode.set("set_params", delParams);

        try {
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

    private void showResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");

            if(status.equals("OK")){
                Toast.makeText((SetActivityView)getActivity(), "Ustawienie zmienione", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText((SetActivityView)getActivity(), "Błąd", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
