using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class gamemanger : MonoBehaviour
{
    // Start is called before the first frame update

    static gamemanger instance;
    public GameObject gameoverUI;
    public Text score;
    public GameObject gameover;
    public GameObject picture;

    void Update()
    {
        score.text = "你通过了 " + GM.score + " 对叉子";
        if (GM.isActive)
        {
            gameover.SetActive(false);
            picture.SetActive(false);
            gameoverUI.SetActive(false);
        }
        if (!GM.isActive && GM.score < 10)
        {
            gameover.SetActive(true);
            gameoverUI.SetActive(true);
        }
        if (!GM.isActive && GM.score >= 10)
        {
            picture.SetActive(true);
        }
    }

    private void Awake()
    {
        if (instance != null)
        {
            Destroy(gameObject);
        }
        instance = this;
    }

    // Update is called once per frame
    public void RestartGame()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().name);
        Time.timeScale = 1;
        GM.isActive = true;
        GM.score = 0;
    }
    public void Quit()
    {
        Application.Quit();
    }
    public static void Gameover(bool dead)
    {
        if (dead)
        {
            instance.gameoverUI.SetActive(true);
            Time.timeScale = 0f;
            GM.score = 0;
        }
    }
}
