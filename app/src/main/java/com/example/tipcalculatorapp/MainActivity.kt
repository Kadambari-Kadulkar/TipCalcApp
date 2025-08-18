package com.example.tipcalculatorapp

import android.content.res.Resources
import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculatorapp.components.InputField
import com.example.tipcalculatorapp.ui.theme.TipCalculatorAppTheme
import com.example.tipcalculatorapp.util.calcTotalTip
import com.example.tipcalculatorapp.util.calculateTotalPerPerson
import com.example.tipcalculatorapp.widgets.RoundIconButton
import kotlin.jvm.Throws

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                TipCalculator()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit){
    TipCalculatorAppTheme {
        Surface(color = MaterialTheme.colorScheme.background){
            content()
            //Text("Hello there!")
        }


    }
}


@Composable
fun TopHeader(totalPerPerson : Double = 134.0){
    Surface (
        Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))), color =  colorResource(R.color.light_pink)
    ){
            Column (Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ){
            val total = "%.2f".format(totalPerPerson)
            Text(text = stringResource(R.string.total_per_person),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
            Text(text = "£$total",
                modifier = Modifier.testTag("TotalPerPerson"),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}


@Preview
//@Composable
//fun MainContent(){
//    BillForm { billAmt ->
//        Log.d("billAmount", billAmt)
//
//    }
//}

@Composable
fun MainContent() {
    val splitBy = remember {
        mutableStateOf(1)
    }

//    val sliderPosition: MutableState<Float> = remember {
//        mutableStateOf(0f)
//    }

    val totalTipAmt = remember {
        mutableStateOf(0.0)
    }
//    val totalTipAmt: MutableState<Double> = remember {
//        mutableStateOf(0.0)
//    }
    val totalPerPerson = remember {
        mutableStateOf(0.0)
    }
    BillForm(splitByState = splitBy,
        tipAmountState = totalTipAmt,
        totalPerPersonState = totalPerPerson

    ) {

    }
}




@Composable
fun BillForm(modifier: Modifier = Modifier,
             range: IntRange = 1..20,
             splitByState: MutableState<Int>,
             tipAmountState: MutableState<Double>,
             totalPerPersonState: MutableState<Double>,
             onValChange: (String) -> Unit = {}){

    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val sliderPositionState = remember {
        mutableStateOf(0f)
    }

    val tipPercent = (sliderPositionState.value * 100).toInt()

//    val tipAmountState = remember {
//        mutableStateOf(0.0)
//    }
//
//    val splitByState = remember {
//        mutableStateOf(1)
//    }

  //  val totalPerPersonState = remember(0.0) { mutableStateOf(0.0) }

   // val range = IntRange(start = 1, endInclusive = 20)

    TopHeader(totalPerPersonState.value)
    Surface(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {
            InputField(valueState = totalBillState,
                labelId = stringResource(R.string.enter_bill),
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions{
                    if(!validState) return@KeyboardActions
                    onValChange(totalBillState.value.trim())
                    keyboardController?.hide()
                })

            if (validState) {

                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.Start
                )
                {

                    Text(stringResource(R.string.split), modifier = Modifier.align(alignment = Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(145.dp))
                    Row (modifier =  Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End) {

                        RoundIconButton(modifier = modifier.align(Alignment.CenterVertically).padding(start = 1.dp, top = 8.dp).testTag("RemoveButton"),
                            imageVector = Icons.Default.Remove,
                            onClick = {
                            //Log.d("RoundIconButton","Remove clicked")
                                splitByState.value =
                                    if(splitByState.value > 1) splitByState.value - 1 else 1
                                totalPerPersonState.value = calculateTotalPerPerson(totalBillState.value.toDouble(), splitByState.value, tipPercent)
                            })
                        Text("${splitByState.value}", Modifier.padding(start = 9.dp, end = 9.dp).align(Alignment.CenterVertically))

                        RoundIconButton(modifier = modifier.align(Alignment.CenterVertically).padding(start = 1.dp, top = 8.dp).testTag("AddButton"),
                            imageVector = Icons.Default.Add, onClick = {
                            //Log.d("RoundIconButton","Add clicked")
                                if (splitByState.value < range.last){
                                    splitByState.value += 1
                                    totalPerPersonState.value = calculateTotalPerPerson(totalBillState.value.toDouble(), splitByState.value, tipPercent)
                                }
                        })
                    }

                }
                //Tip Row
                Row(modifier = Modifier.padding(horizontal = 3.dp, vertical = 12.dp)){
                    Text(text = stringResource(R.string.tip), modifier = modifier.align(Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(200.dp))
                    Text(text = "£${tipAmountState.value}", modifier = Modifier.align(Alignment.CenterVertically))
                }
                    Column ( verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                        Text(text= "${tipPercent}%")
                        Spacer(modifier = modifier.height(14.dp))
                        Slider(value = sliderPositionState.value, onValueChange = { newVal ->
                            sliderPositionState.value = newVal


                            tipAmountState.value = calcTotalTip(totalBillState.value.toDouble(), tipPercent)

                            totalPerPersonState.value = calculateTotalPerPerson(totalBillState.value.toDouble(), splitByState.value, tipPercent)

                        }, modifier = Modifier.padding(start = 16.dp, end = 16.dp).testTag("TipSlider"),  onValueChangeFinished = {

                        })

                    }
            }
        }
    }
}



@Composable
fun TipCalculator() {
    Surface(modifier = Modifier.padding(12.dp)) {
        Column() {
            MainContent()
        }
    }
}

//@Preview
@Composable
fun DefaultPreview() {
    TipCalculatorAppTheme {
        MyApp { Text("Hello there!") }
    }
}

