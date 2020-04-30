using System.Collections;
using System.Collections.Generic;
using UnityEngine.UI;
using UnityEngine;

public class UIUI : MonoBehaviour
{

    public Text score;
    public GameObject gameover;
    public GameObject picture;
    public GameObject panel;
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        score.text = "你通过了 " + GM.score + " 对叉子";
        if (GM.isActive)
        {
            gameover.SetActive(false);
            picture.SetActive(false);
            panel.SetActive(false);
        }
        if (!GM.isActive&&GM.score<10)
        {
            gameover.SetActive(true);
            panel.SetActive(true);
        }
        if (!GM.isActive && GM.score >= 10)
        {
            picture.SetActive(true);
        }
    }
}
